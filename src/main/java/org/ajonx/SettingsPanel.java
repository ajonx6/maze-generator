package org.ajonx;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {
	private final Maze maze;
	private final JFrame frame;
	private final MazePanel mazePanel;

	private JTabbedPane tabbedPane;
	private JPanel generatorSettings;
	private JPanel searchSettings;
	private JPanel resetTransformPanel;
	private JButton resetZoom;
	private JButton resetTranslation;

	public SettingsPanel(Maze maze, JFrame frame, MazePanel mazePanel) {
		this.maze = maze;
		this.frame = frame;
		this.mazePanel = mazePanel;

		createUI();
	}

	public void createUI() {
		setLayout(new BorderLayout());

		tabbedPane = new JTabbedPane();

		generatorSettings = new GeneratorSettingsPanel(maze, frame, mazePanel);
		tabbedPane.addTab("Generator", generatorSettings);

		searchSettings = new SearchSettingsPanel(maze, frame, mazePanel);
		tabbedPane.addTab("Search", searchSettings);

		resetTransformPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		resetZoom = new JButton("Reset zoom");
		resetZoom.addActionListener(_ -> mazePanel.setZoom(1.0));
		resetTransformPanel.add(resetZoom);

		resetTranslation = new JButton("Reset offset");
		resetTranslation.addActionListener(_ -> mazePanel.setOffset(0.0, 0.0));
		resetTransformPanel.add(resetTranslation);

		add(tabbedPane, BorderLayout.CENTER);
		add(resetTransformPanel, BorderLayout.SOUTH);
	}
}