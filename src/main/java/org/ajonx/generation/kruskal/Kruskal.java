package org.ajonx.generation.kruskal;

import org.ajonx.Maze;
import org.ajonx.ui.MazePanel;
import org.ajonx.generation.MazeGenerator;
import org.ajonx.generation.MazeGeneratorStyles;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kruskal extends MazeGenerator {
	private List<Wall> walls = new ArrayList<>();
	private int wallIndex = 0;
	private DisjointSet disjointSet;

	private int[] grid;
	private KruskalStyles styles;

	public Kruskal(Maze maze, MazePanel mazePanel, int delayMs, Frame frame) {
		super(maze, mazePanel, delayMs);
		this.styles = (KruskalStyles) createStyles();
		this.dialogBox = new KruskalDialogBox(frame, styles);
	}

	public void prepare(int seed) {
		super.prepare(seed);

		grid = new int[width * height];
		walls.clear();
		wallIndex = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x < width - 1) walls.add(new Wall(x + y * width, (x + 1) + y * width));
				if (y < height - 1) walls.add(new Wall(x + y * width, x + (y + 1) * width));
			}
		}
		Collections.shuffle(walls, random);

		disjointSet = new DisjointSet(width * height);
	}

	public boolean step() {
		if (wallIndex >= walls.size()) return true;

		maze.clearColors();
		Wall wall = walls.get(wallIndex++);

		if (disjointSet.union(wall.i1, wall.i2)) {
			removeWallBetween(wall);
			colorCells(wall, styles.getWillConnectCellFloor(), styles.getWillConnectCellWall());
		} else {
			colorCells(wall, styles.getWontConnectCellFloor(), styles.getWontConnectCellWall());
		}

		maze.setGrid(grid);
		return false;
	}

	private void removeWallBetween(Wall wall) {
		boolean horizontal = wall.i1 / width == wall.i2 / width;
		grid[wall.i1] |= (horizontal ? (1 << Maze.RIGHT) : (1 << Maze.DOWN));
		grid[wall.i2] |= (horizontal ? (1 << Maze.LEFT) : (1 << Maze.UP));
	}

	private void colorCells(Wall wall, int floorColor, int wallColor) {
		maze.setFloorColor(wall.i1 % width, wall.i1 / width, floorColor);
		maze.setWallColor(wall.i2 % width, wall.i2 / width, wallColor);
	}

	public void stepAll() {
		for (int i = 0; i < walls.size(); i++) {
			step();
		}

		maze.clearColors();
		maze.setFloorColor(0, 0, 0x00ff00);
		maze.setFloorColor(width - 1, height - 1, 0xff0000);
	}

	public MazeGeneratorStyles createStyles() {
		return new KruskalStyles(0x00ff00, 0x00ff00, 0xff0000, 0xff0000);
	}

	public String getName() {
		return "Kruskal";
	}
}
