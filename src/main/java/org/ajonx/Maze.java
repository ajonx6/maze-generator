package org.ajonx;

import java.util.Arrays;
import java.util.Random;

public class Maze {
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int BLOCK_SIZE = 3;

	private int width, height;
	private int[] grid;
	private int[] floorColor;
	private int[] wallColor;
	private boolean hasGenerated = false;

	public Maze(int width, int height) {
		this.width = width;
		this.height = height;
		resize();
	}

	public boolean isConnected(int x1, int y1, int x2, int y2) {
		int state1 = grid[x1 + y1 * width];
		int state2 = grid[x2 + y2 * width];
		int dx = x2 - x1;
		int dy = y2 - y1;

		boolean connected = false;

		if (dx == 0 && dy == -1) {
			connected = (((state1 >> UP) & 1) == 1) && (((state2 >> DOWN) & 1) == 1);
		} else if (dx == 0 && dy == 1) {
			connected = (((state1 >> DOWN) & 1) == 1) && (((state2 >> UP) & 1) == 1);
		} else if (dx == -1 && dy == 0) {
			connected = (((state1 >> LEFT) & 1) == 1) && (((state2 >> RIGHT) & 1) == 1);
		} else if (dx == 1 && dy == 0) {
			connected = (((state1 >> RIGHT) & 1) == 1) && (((state2 >> LEFT) & 1) == 1);
		}

		return connected;
	}

	public void resize() {
		this.grid = new int[width * height];
		this.floorColor = new int[width * height];
		this.wallColor = new int[width * height];
	}

	public void clearColors() {
		Arrays.fill(floorColor, 0xffffff);
		Arrays.fill(wallColor, 0x000000);
	}

	public void setFloorColor(int x, int y, int color) {
		floorColor[x + y * width] = color;
	}

	public void setWallColor(int x, int y, int color) {
		wallColor[x + y * width] = color;
	}

	// Returns the 3x3 pixels for a particular cell
	public int[] getCellPixels(int x, int y) {
		int[] cell = new int[BLOCK_SIZE * BLOCK_SIZE];
		if (grid[x + y * width] == 0) return cell;

		Arrays.fill(cell, wallColor[x + y * width]);
		cell[4] = floorColor[x + y * width];

		int value = grid[x + y * width];
		if (((value >> UP) & 0x01) == 1) cell[1] = floorColor[x + y * width];
		if (((value >> DOWN) & 0x01) == 1) cell[7] = floorColor[x + y * width];
		if (((value >> LEFT) & 0x01) == 1) cell[3] = floorColor[x + y * width];
		if (((value >> RIGHT) & 0x01) == 1) cell[5] = floorColor[x + y * width];

		return cell;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		resize();
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
		resize();
	}

	public void setGrid(int[] grid) {
		System.arraycopy(grid, 0, this.grid, 0, grid.length);
	}

	public boolean hasGenerated() {
		return hasGenerated;
	}

	public void hasGenerated(boolean hasGenerated) {
		this.hasGenerated = hasGenerated;
	}
}