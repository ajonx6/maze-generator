package org.ajonx.generation.kruskal;

import org.ajonx.generation.MazeGeneratorStyles;

public class KruskalStyles implements MazeGeneratorStyles {
	private int willConnectCellFloor;
	private int willConnectCellWall;
	private int wontConnectCellFloor;
	private int wontConnectCellWall;

	public KruskalStyles(int willConnectCellFloor, int willConnectCellWall, int wontConnectCellFloor, int wontConnectCellWall) {
		this.willConnectCellFloor = willConnectCellFloor;
		this.willConnectCellWall = willConnectCellWall;
		this.wontConnectCellFloor = wontConnectCellFloor;
		this.wontConnectCellWall = wontConnectCellWall;
	}

	public int getWillConnectCellFloor() {
		return willConnectCellFloor;
	}

	public void setWillConnectCellFloor(int willConnectCellFloor) {
		this.willConnectCellFloor = willConnectCellFloor;
	}

	public int getWillConnectCellWall() {
		return willConnectCellWall;
	}

	public void setWillConnectCellWall(int willConnectCellWall) {
		this.willConnectCellWall = willConnectCellWall;
	}

	public int getWontConnectCellFloor() {
		return wontConnectCellFloor;
	}

	public void setWontConnectCellFloor(int wontConnectCellFloor) {
		this.wontConnectCellFloor = wontConnectCellFloor;
	}

	public int getWontConnectCellWall() {
		return wontConnectCellWall;
	}

	public void setWontConnectCellWall(int wontConnectCellWall) {
		this.wontConnectCellWall = wontConnectCellWall;
	}
}