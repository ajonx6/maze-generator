package org.ajonx.generation.dfs;

import org.ajonx.Maze;
import org.ajonx.MazePanel;
import org.ajonx.generation.MazeGenerator;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class DepthFirstSearch extends MazeGenerator {
	private int[] grid;
	private boolean[] seen;
	private final Stack<Point> stack = new Stack<>();
	private final DepthFirstSearchStyles styles;

	public DepthFirstSearch(Maze maze, MazePanel mazePanel, int delayMs, Frame frame) {
		super(maze,
			  mazePanel,
			  delayMs,
			  new DepthFirstSearchStyles(0x00ff00, 0x00ff00, 0x0000ff, 0x0000ff, 0xff0000, 0xff0000)
		);
		this.styles = (DepthFirstSearchStyles) super.styles;
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
		List<Integer> dirsToCheck = Arrays.asList(Maze.UP, Maze.DOWN, Maze.LEFT, Maze.RIGHT);
		Collections.shuffle(dirsToCheck, random);

		boolean pickedDir = false;
		boolean done = false;
		for (int dirIndex : dirsToCheck) {
			int nx = current.x + directions[dirIndex][0];
			int ny = current.y + directions[dirIndex][1];

			if (nx >= 0 && ny >= 0 && nx < width && ny < height && !seen[nx + ny * width]) {
				maze.setWallColor(nx, ny, styles.getCandidateCellFloor());
				maze.setFloorColor(nx, ny, styles.getCandidateCellWall());
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
			maze.setFloorColor(current.x, current.y, styles.getBacktrackFloor());
			maze.setWallColor(current.x, current.y, styles.getBacktrackWall());
			stack.pop();
		} else {
			maze.setFloorColor(current.x, current.y, styles.getCurrentCellFloor());
			maze.setWallColor(current.x, current.y, styles.getCurrentCellWall());
		}

		maze.setGrid(grid);
		return false;
	}

	public void stepAll() {
		while (!stack.isEmpty()) {
			step();
		}

		maze.clearColors();
		maze.setFloorColor(0, 0, 0x00ff00);
		maze.setFloorColor(width - 1, height - 1, 0xff0000);
	}

	public String getName() {
		return "Depth-First Search";
	}
}
