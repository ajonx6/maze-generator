package org.ajonx.search.astar;

import org.ajonx.Maze;
import org.ajonx.MazePanel;
import org.ajonx.search.Path;
import org.ajonx.search.SearchAlgorithm;
import org.ajonx.search.SearchAlgorithmStyles;
import org.ajonx.search.bfs.BFSDialogBox;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class AStar extends SearchAlgorithm {
	private static final List<Integer> DIRS_TO_CHECK = Arrays.asList(Maze.UP, Maze.DOWN, Maze.LEFT, Maze.RIGHT);

	private PriorityQueue<Path> toCheck = new PriorityQueue<>(Comparator.comparingInt(p -> p.cost(width - 1, height - 1)));
	private Path previous = null;
	private boolean[] seen;
	private AStarStyles styles;

	public AStar(Maze maze, MazePanel mazePanel, int delayMs, JFrame frame) {
		super(maze, mazePanel, delayMs);
		this.styles = (AStarStyles) createStyles();
		this.dialogBox = new AStarDialogBox(frame, styles);
	}

	public void prepare() {
		super.prepare();

		maze.clearColors();

		seen = new boolean[width * height];
		toCheck.clear();

		int startX = 0, startY = 0;
		toCheck.add(new Path(new Point(startX, startY), null, new ArrayList<>()));
		seen[0] = true;
	}

	public boolean step() {
		if (toCheck.isEmpty()) return true;

		if (previous != null) {
			colorCells(previous.point.x, previous.point.y, styles.getSeenCellFloor(), styles.getSeenCellWall());
		}

		Path path = toCheck.poll();
		if (path == null) return false;
		seen[path.point.x + path.point.y * width] = true;
		previous = path;
		colorCells(path.point.x, path.point.y, styles.getCurrentCellFloor(), styles.getCurrentCellWall());

		for (int dirIndex : DIRS_TO_CHECK) {
			int nx = path.point.x + directions[dirIndex][0];
			int ny = path.point.y + directions[dirIndex][1];

			if (inbounds(nx, ny) && !seen[nx + ny * width] && maze.isConnected(path.point.x, path.point.y, nx, ny)) {
				Path newPath = new Path(new Point(nx, ny), path.point, path.path);
				if (nx == width - 1 && ny == height - 1) {
					previous = newPath;
					return true;
				}

				colorCells(nx, ny, styles.getQueuedCellFloor(), styles.getQueuedCellWall());
				toCheck.add(newPath);
			}
		}

		return false;
	}

	public void end() {
		maze.clearColors();

		boolean previousIsEnd = previous.point.x == width - 1 && previous.point.y == height - 1;
		if (!previousIsEnd) return;

		int floorColor = styles.getPathCellFloor();
		int wallColor = styles.getPathCellWall();
		colorCells(previous.point.x, previous.point.y, floorColor, wallColor);
		for (Point p : previous.path) {
			colorCells(p.x, p.y, floorColor, wallColor);
		}

		maze.setFloorColor(0, 0, 0x00ff00);
		maze.setFloorColor(width - 1, height - 1, 0xff0000);
	}

	public boolean inbounds(int x, int y) {
		return x >= 0 && y >= 0 && x < width && y < height;
	}

	private void colorCells(int x, int y, int floorColor, int wallColor) {
		maze.setFloorColor(x, y, floorColor);
		maze.setWallColor(x, y, wallColor);
	}

	public void stepAll() {
		while (!step()) {}
		end();
	}

	public SearchAlgorithmStyles createStyles() {
		return new AStarStyles(0x00ff00, 0x00ff00, 0x0000ff, 0x0000ff, 0xff0000, 0x000000, 0xebab34, 0x000000);
	}

	public String getName() {
		return "A*";
	}
}