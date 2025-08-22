package org.ajonx.ui.heatmap;

public class HeatmapStyles {
	private int closeColor;
	private int midColor;
	private int farColor;

	public HeatmapStyles(int closeColor, int midColor, int farColor) {
		this.closeColor = closeColor;
		this.midColor = midColor;
		this.farColor = farColor;
	}

	public int getCloseColor() {
		return closeColor;
	}

	public void setCloseColor(int closeColor) {
		this.closeColor = closeColor;
	}

	public int getMidColor() {
		return midColor;
	}

	public void setMidColor(int midColor) {
		this.midColor = midColor;
	}

	public int getFarColor() {
		return farColor;
	}

	public void setFarColor(int farColor) {
		this.farColor = farColor;
	}
}
