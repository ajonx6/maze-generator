package org.ajonx.ui;

import org.ajonx.Maze;
import org.ajonx.generation.MazeGenerator;
import org.ajonx.ui.heatmap.HeatmapDialogBox;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MiscPanel extends JPanel {
	public List<JComponent> activeOnMazeGen = new ArrayList<>();

	private final Maze maze;
	private final JFrame frame;
	private final MazePanel mazePanel;

	private JButton generateHeatmapButton;
	private JButton heatmapStyles;

	public MiscPanel(Maze maze, JFrame frame, MazePanel mazePanel) {
		this.maze = maze;
		this.frame = frame;
		this.mazePanel = mazePanel;

		createUI();
	}

	public void createUI() {
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(200, 0));
		this.setMaximumSize(new Dimension(200, Integer.MAX_VALUE));
		this.setMinimumSize(new Dimension(200, 0));


		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridwidth = 1;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;

		generateHeatmapButton = new JButton("Generate Heatmap");
		generateHeatmapButton.addActionListener(e -> {
			maze.generateHeatmap();
			frame.repaint();
		});
		activeOnMazeGen.add(generateHeatmapButton);

		heatmapStyles = new JButton("Open Heatmap Styling");
		heatmapStyles.addActionListener(e -> {
			HeatmapDialogBox dialogBox = new HeatmapDialogBox(frame, maze.getHeatmapStyles());
			dialogBox.setVisible(true);
		});

		// JButton openStylesButton = new JButton("\u2699");
		// openStylesButton.setFont(new Font("Segoe UI Symbol", Font.BOLD, 24));
		// openStylesButton.setPreferredSize(new Dimension(24, 24));
		// openStylesButton.setMargin(new Insets(0,0,0,0));
		// openStylesButton.setContentAreaFilled(false);
		// openStylesButton.setFocusPainted(false);
		// openStylesButton.setBorderPainted(false);
		// openStylesButton.addActionListener(e -> {
		//     JOptionPane.showMessageDialog(frame, "Open Styles clicked!");
		// });

		int gridy = 0;
		gbc.insets = new Insets(5, 10, 0, 10);
		gbc.gridy = gridy++;
		add(generateHeatmapButton, gbc);

		gbc.insets = new Insets(5, 10, 10, 10);
		gbc.gridy = gridy++;
		add(heatmapStyles, gbc);


		// gbc.insets = new Insets(5, 10, 10, 10);
		// gbc.gridy = gridy++;
		// add(openStylesButton, gbc);

		gbc.gridy = gridy++;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.VERTICAL;
		add(Box.createVerticalGlue(), gbc);
	}
}