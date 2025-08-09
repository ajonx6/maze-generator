package org.ajonx.search;

import org.ajonx.Maze;
import org.ajonx.MazePanel;
import org.ajonx.generation.MazeGeneratorDialogBox;

import javax.swing.*;
import java.util.Random;

public abstract class SearchAlgorithm {
	protected int[][] directions = { { 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 } };

	protected Maze maze;
	protected MazePanel mazePanel;

	protected Timer timer;
	protected int width, height;
	protected SearchAlgorithmDialogBox dialogBox;

	public SearchAlgorithm(Maze maze, MazePanel mazePanel, int delayMs) {
		this.maze = maze;
		this.mazePanel = mazePanel;
		timer = new Timer(delayMs, null);
		timer.addActionListener(_ -> {
			boolean done = step();
			mazePanel.repaint();
			if (done) {
				timer.stop();
				end();
			}
		});
	}

	public void prepare() {
		width = maze.getWidth();
		height = maze.getHeight();
	}

	public void start() {
		if (timer.isRunning()) timer.stop();
		prepare();
		if (timer.getDelay() == 0) stepAll();
		else timer.start();
	}

	public void stop() {
		if (timer.isRunning()) {
			timer.stop();
			end();
		}
	}

	public boolean toggle() {
		if (timer.isRunning()) {
			timer.stop();
			return true;
		} else {
			timer.start();
			return false;
		}
	}

	public void setDelay(int delayMs) {
		timer.setDelay(delayMs);
	}
	public SearchAlgorithmDialogBox getDialogBox() {
		return dialogBox;
	}

	public abstract boolean step();

	public abstract void stepAll();

	public abstract void end();

	public abstract SearchAlgorithmStyles createStyles();

	public abstract String getName();
}