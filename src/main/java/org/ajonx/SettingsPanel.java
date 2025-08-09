package org.ajonx;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {
	private final Maze maze;
	private final JFrame frame;
	private final MazePanel mazePanel;

	private JTabbedPane tabbedPane;

	public SettingsPanel(Maze maze, JFrame frame, MazePanel mazePanel) {
		this.maze = maze;
		this.frame = frame;
		this.mazePanel = mazePanel;

		createUI();
	}

	public void createUI() {
		tabbedPane = new JTabbedPane();

		JPanel generatorSettings = new GeneratorSettingsPanel(maze, frame, mazePanel);
		tabbedPane.addTab("Generator", generatorSettings);

		JPanel searchSettings = new SearchSettingsPanel(maze, frame, mazePanel);
		tabbedPane.addTab("Search", searchSettings);

		setLayout(new BorderLayout());
		add(tabbedPane, BorderLayout.CENTER);
	}
}