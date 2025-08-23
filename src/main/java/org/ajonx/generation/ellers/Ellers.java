package org.ajonx.generation.ellers;

import org.ajonx.Maze;
import org.ajonx.generation.MazeGenerator;
import org.ajonx.generation.MazeGeneratorStyles;
import org.ajonx.ui.MazePanel;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Ellers extends MazeGenerator {
	private static final double DECAY_FACTOR = 0.25;

	private int state = 0;
	private int[] setIds;
	private Map<Integer, List<Integer>> setIdToIndex = new HashMap<>();
	private Set<Integer> indicesToMoveDown = new HashSet<>();
	private int nextSetID = 0;
	private int currX = 0, currY = 0;

	private int[] grid;
	private EllersStyles styles;

	public Ellers(Maze maze, MazePanel mazePanel, int delayMs, Frame frame) {
		super(maze, mazePanel, delayMs);
		this.styles = (EllersStyles) createStyles();
		this.dialogBox = new EllersDialogBox(frame, styles);
	}

	public void prepare(int seed) {
		super.prepare(seed);

		grid = new int[width * height];
		setIds = new int[width * height];
		Arrays.fill(setIds, -1);
		nextSetID = 0;
		setIdToIndex.clear();
		indicesToMoveDown.clear();
		state = 0;

		for (int x = 0; x < width; x++) {
			setIds[x] = nextSetID++;
		}

		currX = 0;
		currY = 0;
	}

	public void moveIdToNewId(int oldId, int newId) {
		for (int i = 0; i < setIds.length; i++) {
			if (setIds[i] == oldId) setIds[i] = newId;
		}
	}

	public boolean step() {
		maze.clearColors();

		boolean isLastRow = (currY == height - 1);

		if (state == 0) {
			int setI = setIds[currX + currY * width];
			int setNI = setIds[(currX + 1) + currY * width];

			if (setI == setNI) {
				colorCells(currX, currY, styles.getUnchangedFloor(), styles.getUnchangedWall());
				colorCells(currX + 1, currY, styles.getUnchangedFloor(), styles.getUnchangedWall());
			} else {
				boolean breakWall = isLastRow || random.nextBoolean();

				if (breakWall) {
					grid[currX + currY * width] |= 1 << Maze.RIGHT;
					grid[(currX + 1) + currY * width] |= 1 << Maze.LEFT;

					moveIdToNewId(setI, setNI);

					colorCells(currX, currY, styles.getChangeFloor(), styles.getChangeWall());
					colorCells(currX + 1, currY, styles.getChangeFloor(), styles.getChangeWall());
				} else {
					colorCells(currX, currY, styles.getDontChangeFloor(), styles.getDontChangeWall());
					colorCells(currX + 1, currY, styles.getDontChangeFloor(), styles.getDontChangeWall());
				}
			}


			currX++;
			if (currX == width - 1) {
				state++;
			}
		} else if (state == 1) {
			if (setIdToIndex.isEmpty()) {
				for (int x = 0; x < width; x++) {
					int setId = setIds[x + currY * width];
					setIdToIndex.computeIfAbsent(setId, _ -> new ArrayList<>()).add(x);
				}

				for (int setId : setIdToIndex.keySet()) {
					int numPicked = 1;
					List<Integer> xValues = setIdToIndex.get(setId);
					double p = DECAY_FACTOR;
					for (int i = 1; i < xValues.size(); i++) {
						if (random.nextDouble() < p) numPicked++;
						p *= DECAY_FACTOR;
					}
					for (int i = 0; i < numPicked; i++) {
						int x = xValues.remove(random.nextInt(xValues.size()));
						indicesToMoveDown.add(x);
					}
				}
			}
			currX = 0;
			state++;
		} else {
			if (indicesToMoveDown.contains(currX)) {
				grid[currX + currY * width] |= 1 << Maze.DOWN;
				grid[currX + (currY + 1) * width] |= 1 << Maze.UP;

				colorCells(currX, currY, styles.getChangeFloor(), styles.getChangeWall());
				colorCells(currX, currY + 1, styles.getChangeFloor(), styles.getChangeWall());
			} else {
				colorCells(currX, currY, styles.getDontChangeFloor(), styles.getDontChangeWall());
				colorCells(currX, currY + 1, styles.getDontChangeFloor(), styles.getDontChangeWall());
			}

			currX++;
			if (currX == width) {
				currX = 0;
				currY++;
				setIdToIndex.clear();
				indicesToMoveDown.clear();
				state = 0;

				for (int xx = 0; xx < width; xx++) {
					if (setIds[xx + currY * width] == -1) setIds[xx + currY * width] = nextSetID++;
				}
			}
		}

		maze.setGrid(grid);
		return isLastRow && state == 1;
	}

	private void colorCells(int x, int y, int floorColor, int wallColor) {
		maze.setFloorColor(x, y, floorColor);
		maze.setWallColor(x, y, wallColor);
	}

	public void stepAll() {
		while (!step()) {}

		maze.clearColors();
		maze.setFloorColor(0, 0, 0x00ff00);
		maze.setFloorColor(width - 1, height - 1, 0xff0000);
	}

	public MazeGeneratorStyles createStyles() {
		return new EllersStyles(0x0000ff, 0x0000ff, 0x00ff00, 0x00ff00, 0xff0000, 0xff0000, 0xebab34, 0xebab34);
	}

	public String getName() {
		return "Ellers";
	}
}
