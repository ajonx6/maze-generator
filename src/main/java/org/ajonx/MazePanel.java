package org.ajonx;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class MazePanel extends JPanel {
	private final Maze maze;

	public MazePanel(Maze maze) {
		this.maze = maze;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();

		if (!maze.hasGenerated()) {
			g2d.setColor(Color.BLACK);
			g2d.fill(new Rectangle2D.Double(0, 0, 1, 1));
			g2d.dispose();
			return;
		}

		int mazeWidth = maze.getWidth();
		int mazeHeight = maze.getHeight();

		int containerWidth = getWidth();
		int containerHeight = getHeight();

		double cellWidth = (double) containerWidth / mazeWidth;
		double cellHeight = (double) containerHeight / mazeHeight;
		// Size each cell should be in pixels, taking the min so they stay square but maximise size in panel
		double cellSize = Math.min(cellWidth, cellHeight);
		double pixelSize = cellSize / Maze.BLOCK_SIZE;

		double drawWidth = cellSize * mazeWidth;
		double drawHeight = cellSize * mazeHeight;

		double offsetX = (containerWidth - drawWidth) / 2.0;
		double offsetY = (containerHeight - drawHeight) / 2.0;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

		for (int y = 0; y < mazeHeight; y++) {
			for (int x = 0; x < mazeWidth; x++) {
				int[] cellPixels = maze.getCellPixels(x, y);
				double baseX = offsetX + x * cellSize;
				double baseY = offsetY + y * cellSize;

				for (int dy = 0; dy < Maze.BLOCK_SIZE; dy++) {
					for (int dx = 0; dx < Maze.BLOCK_SIZE; dx++) {
						double px = baseX + dx * pixelSize;
						double py = baseY + dy * pixelSize;

						g2d.setColor(new Color(cellPixels[dx + dy * Maze.BLOCK_SIZE]));

						// Draw aligned to integer boundaries to avoid artifacts
						g2d.fill(new Rectangle2D.Double(
								Math.floor(px),
								Math.floor(py),
								Math.ceil(pixelSize),
								Math.ceil(pixelSize)
						));
					}
				}
			}
		}

		g2d.dispose();
	}
}