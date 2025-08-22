package org.ajonx.ui;

import javax.swing.*;
import java.awt.*;

// A rounded rectangle filled with a color
public class ColorPreviewBox extends JPanel {
	private Color color;

	public ColorPreviewBox(Color color) {
		this.color = color;
		setPreferredSize(new Dimension(24, 24));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setOpaque(false);
		repaint();
	}

	public void setColor(Color color) {
		this.color = color;
		repaint();
	}

	public Color getColor() {
		return color;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(color);
		g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);

		g2.dispose();
	}
}