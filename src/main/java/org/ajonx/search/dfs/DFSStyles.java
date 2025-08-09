package org.ajonx.search.dfs;

import org.ajonx.search.SearchAlgorithmStyles;

public class DFSStyles implements SearchAlgorithmStyles {
	private int currentCellFloor;
	private int currentCellWall;
	private int queuedCellFloor;
	private int queuedCellWall;
	private int seenCellFloor;
	private int seenCellWall;
	private int pathCellFloor;
	private int pathCellWall;

	public DFSStyles(int currentCellFloor, int currentCellWall, int queuedCellFloor, int queuedCellWall, int seenCellFloor, int seenCellWall, int pathCellFloor, int pathCellWall) {
		this.currentCellFloor = currentCellFloor;
		this.currentCellWall = currentCellWall;
		this.queuedCellFloor = queuedCellFloor;
		this.queuedCellWall = queuedCellWall;
		this.seenCellFloor = seenCellFloor;
		this.seenCellWall = seenCellWall;
		this.pathCellFloor = pathCellFloor;
		this.pathCellWall = pathCellWall;
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

	public int getQueuedCellFloor() {
		return queuedCellFloor;
	}

	public void setQueuedCellFloor(int queuedCellFloor) {
		this.queuedCellFloor = queuedCellFloor;
	}

	public int getQueuedCellWall() {
		return queuedCellWall;
	}

	public void setQueuedCellWall(int queuedCellWall) {
		this.queuedCellWall = queuedCellWall;
	}

	public int getSeenCellFloor() {
		return seenCellFloor;
	}

	public void setSeenCellFloor(int seenCellFloor) {
		this.seenCellFloor = seenCellFloor;
	}

	public int getSeenCellWall() {
		return seenCellWall;
	}

	public void setSeenCellWall(int seenCellWall) {
		this.seenCellWall = seenCellWall;
	}

	public int getPathCellFloor() {
		return pathCellFloor;
	}

	public void setPathCellFloor(int pathCellFloor) {
		this.pathCellFloor = pathCellFloor;
	}

	public int getPathCellWall() {
		return pathCellWall;
	}

	public void setPathCellWall(int pathCellWall) {
		this.pathCellWall = pathCellWall;
	}
}