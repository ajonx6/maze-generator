package org.ajonx.generation.kruskal;

import org.ajonx.Maze;
import org.ajonx.MazePanel;
import org.ajonx.generation.MazeGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Kruskal extends MazeGenerator {
	private List<Wall> walls = new ArrayList<>();
	private int wallIndex = 0;
	private DisjointSet disjointSet;
	private int[] grid;
	private final List<Integer> dirsToCheck = Arrays.asList(Maze.UP, Maze.DOWN, Maze.LEFT, Maze.RIGHT);
	private final KruskalStyles styles;

	private static class DisjointSet {
		private int[] parent;
		private int[] rank;

		public DisjointSet(int cap) {
			this.parent = new int[cap];
			this.rank = new int[cap];
			for (int i = 0; i < cap; i++) {
				parent[i] = i;
			}
		}

		public int find(int n) {
			if (parent[n] != n) {
				parent[n] = find(parent[n]);
			}
			return parent[n];
		}

		public boolean union(int n1, int n2) {
			int rootN1 = find(n1);
			int rootN2 = find(n2);

			if (rootN1 == rootN2) return false;

			if (rank[rootN1] < rank[rootN2]) parent[rootN1] = rootN2;
			else if (rank[rootN2] < rank[rootN1]) parent[rootN2] = rootN1;
			else {
				parent[rootN2] = rootN1;
				rank[rootN1]++;
			}
			return true;
		}
	}

	private static class Wall {
		public int i1, i2;

		public Wall(int i1, int i2) {
			this.i1 = i1;
			this.i2 = i2;
		}
	}

	public Kruskal(Maze maze, MazePanel mazePanel, int delayMs, Frame frame) {
		super(maze,
			  mazePanel,
			  delayMs,
			  new KruskalStyles(0x00ff00, 0x00ff00, 0x0000ff, 0x0000ff, 0xff0000, 0xff0000)
		);
		this.styles = (KruskalStyles) super.styles;
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
		maze.clearColors();

		Wall wall = walls.get(wallIndex++);
		if (disjointSet.union(wall.i1, wall.i2)) {
			boolean horizontal = wall.i1 / width == wall.i2 / width;
			grid[wall.i1] |= (horizontal ? (1 << Maze.RIGHT) : (1 << Maze.DOWN));
			grid[wall.i2] |= (horizontal ? (1 << Maze.LEFT) : (1 << Maze.UP));

			maze.setFloorColor(wall.i1 % width, wall.i1 / width, 0xffffff);
			maze.setFloorColor(wall.i2 % width, wall.i2 / width, 0xffffff);
		}

		maze.setGrid(grid);
		return false;
	}

	public boolean inbounds(int x, int y) {
		return x >= 0 && y >= 0 && x < width && y < height;
	}

	public void stepAll() {
		for (int i = 0; i < walls.size(); i++) {
			step();
		}

		maze.clearColors();
		maze.setFloorColor(0, 0, 0x00ff00);
		maze.setFloorColor(width - 1, height - 1, 0xff0000);
	}

	public String getName() {
		return "Kruskal";
	}
}
