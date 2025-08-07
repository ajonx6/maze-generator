package org.ajonx.generation.prims;

import org.ajonx.Maze;
import org.ajonx.MazePanel;
import org.ajonx.generation.MazeGenerator;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Prims extends MazeGenerator {
	private int[] grid;
	private boolean[] seen;
	private final List<Point> frontier = new ArrayList<>();
	private final List<Integer> dirsToCheck = Arrays.asList(Maze.UP, Maze.DOWN, Maze.LEFT, Maze.RIGHT);
	private final PrimsStyles styles;

	public Prims(Maze maze, MazePanel mazePanel, int delayMs, Frame frame) {
		super(maze,
			  mazePanel,
			  delayMs,
			  new PrimsStyles(0x00ff00, 0x00ff00, 0x0000ff, 0x0000ff, 0xff0000, 0xff0000)
		);
		this.styles = (PrimsStyles) super.styles;
		this.dialogBox = new PrimsDialogBox(frame, styles);
	}

	public void prepare(int seed) {
		super.prepare(seed);

		grid = new int[width * height];
		seen = new boolean[width * height];
		frontier.clear();

		int startX = random.nextInt(width), startY = random.nextInt(height);
		seen[startX + startY * width] = true;
		for (int[] dir : directions) {
			int nx = startX + dir[0];
			int ny = startY + dir[1];
			if (nx >= 0 && ny >= 0 && nx < width && ny < height) {
				frontier.add(new Point(nx, ny));
			}
		}
	}

	public boolean step() {
		if (frontier.isEmpty()) return true;

		maze.clearColors();

		Point current = frontier.remove(random.nextInt(frontier.size()));

		List<Integer> dirsVisited = new ArrayList<>();
		for (int dirIndex : dirsToCheck) {
			int nx = current.x + directions[dirIndex][0];
			int ny = current.y + directions[dirIndex][1];
			if (inbounds(nx, ny) && seen[nx + ny * width]) dirsVisited.add(dirIndex);
		}
		if (dirsVisited.isEmpty()) {
			maze.setFloorColor(current.x, current.y, styles.getBacktrackFloor());
			maze.setWallColor(current.x, current.y, styles.getBacktrackWall());
			return false;
		}

		int dirIndexToConnect = dirsVisited.get(random.nextInt(dirsVisited.size()));
		int px = current.x + directions[dirIndexToConnect][0];
		int py = current.y + directions[dirIndexToConnect][1];

		seen[current.x + current.y * width] = true;
		grid[current.x + current.y * width] |= 1 << dirIndexToConnect;
		grid[px + py * width] |= 1 << (dirIndexToConnect ^ 1);

		for (int dirIndex : dirsToCheck) {
			int nx = current.x + directions[dirIndex][0];
			int ny = current.y + directions[dirIndex][1];
			if (inbounds(nx, ny) && !seen[nx + ny * width]) {
				Point newFrontier = new Point(nx, ny);
				if (!frontier.contains(newFrontier)) frontier.add(newFrontier);
			}
		}

		maze.setFloorColor(current.x, current.y, styles.getCurrentCellFloor());
		maze.setWallColor(current.x, current.y, styles.getCurrentCellFloor());
		maze.setFloorColor(px, py, styles.getCandidateCellFloor());
		maze.setWallColor(px, py, styles.getCandidateCellWall());

		maze.setGrid(grid);
		return false;
	}

	public boolean inbounds(int x, int y) {
		return x >= 0 && y >= 0 && x < width && y < height;
	}

	public void stepAll() {
		while (!frontier.isEmpty()) {
			step();
		}

		maze.clearColors();
		maze.setFloorColor(0, 0, 0x00ff00);
		maze.setFloorColor(width - 1, height - 1, 0xff0000);
	}

	public String getName() {
		return "Prims";
	}
}
