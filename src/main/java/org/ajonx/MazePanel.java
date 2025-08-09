package org.ajonx;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class MazePanel extends JPanel {
	private final Maze maze;

	private double zoom = 1.0;
	private double offsetX = 0.0;
	private double offsetY = 0.0;
	private int lastDragX = 0;
	private int lastDragY = 0;

	public MazePanel(Maze maze) {
		this.maze = maze;

		addMouseWheelListener(e -> {
			if (!maze.hasGenerated()) return;
			double scaleFactor = 1.1;
			if (e.getPreciseWheelRotation() < 0) {
				zoom *= scaleFactor;
			} else {
				zoom /= scaleFactor;
			}
			zoom = Math.max(0.2, Math.min(4.0, zoom));
			repaint();
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!maze.hasGenerated()) return;
				lastDragX = e.getX();
				lastDragY = e.getY();
			}
		});

		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (!maze.hasGenerated()) return;
				offsetX += e.getX() - lastDragX;
				offsetY += e.getY() - lastDragY;
				lastDragX = e.getX();
				lastDragY = e.getY();
				repaint();
			}
		});
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.translate(offsetX, offsetY);
		g2d.scale(zoom, zoom);

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

	public void setZoom(double zoom) {
		this.zoom = zoom;
		repaint();
	}

	public void setOffset(double x, double y) {
		this.offsetX = x;
		this.offsetY = y;
		repaint();
	}
}