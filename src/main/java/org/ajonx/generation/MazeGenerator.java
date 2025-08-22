package org.ajonx.generation;

import org.ajonx.Maze;
import org.ajonx.StylesDialogBox;
import org.ajonx.ui.MazePanel;

import javax.swing.*;
import java.util.Random;

public abstract class MazeGenerator {
	protected int[][] directions = { { 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 } };
	protected Random random = new Random();

	protected Maze maze;
	protected MazePanel mazePanel;

	protected Timer timer;
	protected int width, height;
	protected StylesDialogBox dialogBox;

	public MazeGenerator(Maze maze, MazePanel mazePanel, int delayMs) {
		this.maze = maze;
		this.mazePanel = mazePanel;
		timer = new Timer(delayMs, null);
		timer.addActionListener(_ -> {
			boolean done = step();
			mazePanel.repaint();
			if (done) timer.stop();
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

	public boolean inbounds(int x, int y) {
		return x >= 0 && y >= 0 && x < width && y < height;
	}

	public StylesDialogBox getDialogBox() {
		return dialogBox;
	}

	public abstract boolean step();

	public abstract void stepAll();

	public abstract MazeGeneratorStyles createStyles();

	public abstract String getName();
}