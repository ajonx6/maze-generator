package org.ajonx.generation.dfs;

import org.ajonx.generation.MazeGeneratorStyles;

public class DepthFirstSearchStyles implements MazeGeneratorStyles {
	private int currentCellFloor;
	private int currentCellWall;
	private int candidateCellFloor;
	private int candidateCellWall;
	private int backtrackFloor;
	private int backtrackWall;

	public DepthFirstSearchStyles(int currentCellFloor, int currentCellWall, int candidateCellFloor, int candidateCellWall, int backtrackFloor, int backtrackWall) {
		this.currentCellFloor = currentCellFloor;
		this.currentCellWall = currentCellWall;
		this.candidateCellFloor = candidateCellFloor;
		this.candidateCellWall = candidateCellWall;
		this.backtrackFloor = backtrackFloor;
		this.backtrackWall = backtrackWall;
	}

	public int getCurrentCellFloor() {
		return currentCellFloor;
	}

	public void setCurrentCellFloor(int currentCellFloor) {
		this.currentCellFloor = currentCellFloor;
	}

	public int getCurrentCellWall() {
		return currentCellWall;
	}

	public void setCurrentCellWall(int currentCellWall) {
		this.currentCellWall = currentCellWall;
	}

	public int getCandidateCellFloor() {
		return candidateCellFloor;
	}

	public void setCandidateCellFloor(int candidateCellFloor) {
		this.candidateCellFloor = candidateCellFloor;
	}

	public int getCandidateCellWall() {
		return candidateCellWall;
	}

	public void setCandidateCellWall(int candidateCellWall) {
		this.candidateCellWall = candidateCellWall;
	}

	public int getBacktrackFloor() {
		return backtrackFloor;
	}

	public void setBacktrackFloor(int backtrackFloor) {
		this.backtrackFloor = backtrackFloor;
	}

	public int getBacktrackWall() {
		return backtrackWall;
	}

	public void setBacktrackWall(int backtrackWall) {
		this.backtrackWall = backtrackWall;
	}
}