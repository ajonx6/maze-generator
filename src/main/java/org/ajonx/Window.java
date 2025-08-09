package org.ajonx;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class Window {
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 800;

	private Maze maze;

	private JFrame frame;
	// Panel the size of the maze itself
	private MazePanel mazePanel;
	// Panel to fill the rest of the window
	private MazeWrapperPanel mazeWrapperPanel;
	private SettingsPanel settingsPanel;

	public Window() {
		createUI();
	}

	public void createUI() {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
			FlatLightLaf.setup();
		} catch (UnsupportedLookAndFeelException e) {
			throw new RuntimeException(e);
		}

		frame = new JFrame("Maze Generator");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);

		maze = new Maze(50, 50);
		mazePanel = new MazePanel(maze);
		mazePanel.setBackground(Color.WHITE);
		mazeWrapperPanel = new MazeWrapperPanel(mazePanel);
		mazeWrapperPanel.setBackground(Color.WHITE);

		settingsPanel = new SettingsPanel(maze, frame, mazePanel);

		frame.setLayout(new BorderLayout());
		frame.add(mazeWrapperPanel, BorderLayout.CENTER);
		frame.add(settingsPanel, BorderLayout.EAST);

		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new Window();
	}
}