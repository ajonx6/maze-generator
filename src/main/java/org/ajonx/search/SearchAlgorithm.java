package org.ajonx.search;

import org.ajonx.Maze;
import org.ajonx.MazePanel;

import javax.swing.*;
import java.util.Random;

public abstract class SearchAlgorithm {
	protected int[][] directions = { { 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 } };
	protected Random random = new Random();

	protected Maze maze;
	protected MazePanel mazePanel;

	protected Timer timer;
	protected int width, height;

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

	public void prepare(int seed) {
		width = maze.getWidth();
		height = maze.getHeight();
		random.setSeed(seed);
	}

	public int start(int seed) {
		if (timer.isRunning()) timer.stop();

		int seedToUse = seed >= 0 ? seed : random.nextInt();
		prepare(seedToUse);
		if (timer.getDelay() == 0) stepAll();
		else timer.start();

		return seedToUse;
	}

	public void stop() {
		if (timer.isRunning()) timer.stop();
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

	public abstract boolean step();

	public abstract void stepAll();

	public abstract void end();

	public abstract SearchAlgorithmStyles createStyles();
}