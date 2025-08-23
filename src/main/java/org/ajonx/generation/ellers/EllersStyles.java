package org.ajonx.generation.ellers;

import org.ajonx.generation.MazeGeneratorStyles;

public class EllersStyles implements MazeGeneratorStyles {
	private int unchangedFloor;
	private int unchangedWall;
	private int changeFloor;
	private int changeWall;
	private int dontChangeFloor;
	private int dontChangeWall;
	private int vertFloor;
	private int vertWall;

	public EllersStyles(int unchangedFloor, int unchangedWall, int changeFloor, int changeWall, int dontChangeFloor, int dontChangeWall, int vertFloor, int vertWall) {
		this.unchangedFloor = unchangedFloor;
		this.unchangedWall = unchangedWall;
		this.changeFloor = changeFloor;
		this.changeWall = changeWall;
		this.dontChangeFloor = dontChangeFloor;
		this.dontChangeWall = dontChangeWall;
		this.vertFloor = vertFloor;
		this.vertWall = vertWall;
	}

	public int getUnchangedFloor() {
		return unchangedFloor;
	}

	public void setUnchangedFloor(int unchangedFloor) {
		this.unchangedFloor = unchangedFloor;
	}

	public int getUnchangedWall() {
		return unchangedWall;
	}

	public void setUnchangedWall(int unchangedWall) {
		this.unchangedWall = unchangedWall;
	}

	public int getChangeFloor() {
		return changeFloor;
	}

	public void setChangeFloor(int changeFloor) {
		this.changeFloor = changeFloor;
	}

	public int getChangeWall() {
		return changeWall;
	}

	public void setChangeWall(int changeWall) {
		this.changeWall = changeWall;
	}

	public int getDontChangeFloor() {
		return dontChangeFloor;
	}

	public void setDontChangeFloor(int dontChangeFloor) {
		this.dontChangeFloor = dontChangeFloor;
	}

	public int getDontChangeWall() {
		return dontChangeWall;
	}

	public void setDontChangeWall(int dontChangeWall) {
		this.dontChangeWall = dontChangeWall;
	}

	public int getVertFloor() {
		return vertFloor;
	}

	public void setVertFloor(int vertFloor) {
		this.vertFloor = vertFloor;
	}

	public int getVertWall() {
		return vertWall;
	}

	public void setVertWall(int vertWall) {
		this.vertWall = vertWall;
	}
}