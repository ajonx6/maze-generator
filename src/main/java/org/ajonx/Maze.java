package org.ajonx;

import org.ajonx.search.Path;
import org.ajonx.ui.heatmap.HeatmapStyles;

import java.awt.*;
import java.util.*;
import java.util.List;

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

	private HeatmapStyles heatmapStyles;

	public Maze(int width, int height) {
		this.width = width;
		this.height = height;
		this.heatmapStyles = new HeatmapStyles(0x00ff00, 0xffff00, 0xff0000);
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
		Arrays.fill(cell, wallColor[x + y * width]);
		if (floorColor[x + y * width] == 0xffffff && grid[x + y * width] == 0) return cell;
		cell[4] = floorColor[x + y * width];

		int value = grid[x + y * width];
		if (((value >> UP) & 0x01) == 1) cell[1] = floorColor[x + y * width];
		if (((value >> DOWN) & 0x01) == 1) cell[7] = floorColor[x + y * width];
		if (((value >> LEFT) & 0x01) == 1) cell[3] = floorColor[x + y * width];
		if (((value >> RIGHT) & 0x01) == 1) cell[5] = floorColor[x + y * width];

		return cell;
	}

	public void generateHeatmap() {
		int[][] directions = { { 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 } };
		int maxDist = 0;

		int[] distances = new int[width * height];
		Arrays.fill(distances, -1);
		Queue<Point> toCheck = new LinkedList<>();
		toCheck.add(new Point(width - 1, height - 1));
		distances[(width - 1) + (height - 1) * width] = 0;

		while (!toCheck.isEmpty()) {
			Point point = toCheck.poll();
			for (int[] dir : directions) {
				int nx = point.x + dir[0];
				int ny = point.y + dir[1];

				if (nx < 0 || ny < 0 || nx >= width || ny >= height || distances[nx + ny * width] >= 0 || !isConnected(point.x, point.y, nx, ny)) continue;

				int dist = distances[point.x + point.y * width] + 1;
				distances[nx + ny * width] = dist;
				if (dist > maxDist) maxDist = dist;

				toCheck.add(new Point(nx, ny));
			}
		}

		clearColors();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				double distTotal = (double) distances[x + y * width] / (double) maxDist;

				distTotal = Math.pow(distTotal, 0.9);

				if (distTotal < 0.5) setFloorColor(x, y, colorLerp(heatmapStyles.getCloseColor(), heatmapStyles.getMidColor(), distTotal * 2.0));
				else setFloorColor(x, y, colorLerp(heatmapStyles.getMidColor(), heatmapStyles.getFarColor(), (distTotal - 0.5) * 2.0));
			}
		}
	}

	public int colorLerp(int c1, int c2, double t) {
		t = Math.max(0, Math.min(1, t));

		int r1 = (c1 >> 16) & 0xff;
		int g1 = (c1 >> 8) & 0xff;
		int b1 = c1 & 0xff;

		int r2 = (c2 >> 16) & 0xff;
		int g2 = (c2 >> 8) & 0xff;
		int b2 = c2 & 0xff;

		int r3 = (int) (r1 * (1.0 - t) + r2 * t);
		int g3 = (int) (g1 * (1.0 - t) + g2 * t);
		int b3 = (int) (b1 * (1.0 - t) + b2 * t);

		return (r3 << 16) | (g3 << 8) | b3;
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

	public HeatmapStyles getHeatmapStyles() {
		return heatmapStyles;
	}

	public boolean hasGenerated() {
		return hasGenerated;
	}

	public void hasGenerated(boolean hasGenerated) {
		this.hasGenerated = hasGenerated;
	}
}