package org.ajonx.generation.dfs;

import org.ajonx.Maze;
import org.ajonx.MazePanel;
import org.ajonx.generation.MazeGenerator;
import org.ajonx.generation.MazeGeneratorStyles;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class DepthFirstSearch extends MazeGenerator {
	private static final List<Integer> DIRS_TO_CHECK = Arrays.asList(Maze.UP, Maze.DOWN, Maze.LEFT, Maze.RIGHT);

	private boolean[] seen;
	private Stack<Point> stack = new Stack<>();

	private int[] grid;
	private DepthFirstSearchStyles styles;

	public DepthFirstSearch(Maze maze, MazePanel mazePanel, int delayMs, Frame frame) {
		super(maze, mazePanel, delayMs);
		this.styles = (DepthFirstSearchStyles) createStyles();
		this.dialogBox = new DepthFirstSearchDialogBox(frame, styles);
	}

	public void prepare(int seed) {
		super.prepare(seed);

		grid = new int[width * height];
		seen = new boolean[width * height];
		stack.clear();

		int startX = 0, startY = 0;
		stack.add(new Point(startX, startY));
		seen[0] = true;
	}

	public boolean step() {
		if (stack.isEmpty()) return true;

		maze.clearColors();

		Point current = stack.peek();
		Collections.shuffle(DIRS_TO_CHECK, random);

		boolean pickedDir = false;
		boolean done = false;
		for (int dirIndex : DIRS_TO_CHECK) {
			int nx = current.x + directions[dirIndex][0];
			int ny = current.y + directions[dirIndex][1];

			if (inbounds(nx, ny) && !seen[nx + ny * width]) {
				colorCells(nx, ny, styles.getCandidateCellWall(), styles.getCandidateCellFloor());
				if (done) continue;

				seen[nx + ny * width] = true;

				grid[current.x + current.y * width] |= 1 << dirIndex;
				grid[nx + ny * width] |= 1 << (dirIndex ^ 1);

				if (nx != width - 1 || ny != height - 1) {
					stack.push(new Point(nx, ny));
				}

				pickedDir = true;
				done = true;
			}
		}

		if (!pickedDir) {
			colorCells(current.x, current.y, styles.getBacktrackFloor(), styles.getBacktrackWall());
			stack.pop();
		} else {
			colorCells(current.x, current.y, styles.getCurrentCellFloor(), styles.getCurrentCellWall());
		}

		maze.setGrid(grid);
		return false;
	}

	private void colorCells(int x, int y, int floorColor, int wallColor) {
		maze.setFloorColor(x, y, floorColor);
		maze.setWallColor(x, y, wallColor);
	}

	public void stepAll() {
		while (!stack.isEmpty()) {
			step();
		}

		maze.clearColors();
		maze.setFloorColor(0, 0, 0x00ff00);
		maze.setFloorColor(width - 1, height - 1, 0xff0000);
	}

	public MazeGeneratorStyles createStyles() {
		return new DepthFirstSearchStyles(0x00ff00, 0x00ff00, 0x0000ff, 0x0000ff, 0xff0000, 0xff0000);
	}

	public String getName() {
		return "Depth-First Search";
	}
}
