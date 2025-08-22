package org.ajonx.ui;

import org.ajonx.Maze;
import org.ajonx.search.SearchAlgorithm;
import org.ajonx.search.astar.AStar;
import org.ajonx.search.bfs.BFS;
import org.ajonx.search.dfs.DFS;
import org.ajonx.search.dijkstra.Dijkstra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;

public class SearchSettingsPanel extends JPanel {
	public List<JComponent> activeOnMazeGen = new ArrayList<>();

	private final Maze maze;
	private final JFrame frame;
	private final MazePanel mazePanel;
	private SearchAlgorithm currentAlgorithm;

	private JComboBox<SearchAlgorithm> algorithmDropdown;
	private JButton openStylesButton;
	private JSlider delaySlider;
	private JLabel delaySliderValue;
	private JButton startButton;
	private JButton animationStateButton;

	public SearchSettingsPanel(Maze maze, JFrame frame, MazePanel mazePanel) {
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

		delaySliderValue = new JLabel("Delay = 0ms");

		int minorTickSpacing = 10;
		delaySlider = new JSlider(0, 200, 0);
		delaySlider.setMajorTickSpacing(50);
		delaySlider.setMinorTickSpacing(minorTickSpacing);
		delaySlider.setPaintTicks(true);
		delaySlider.setPaintLabels(true);
		delaySlider.addChangeListener(e -> {
			JSlider source = (JSlider) e.getSource();
			if (!source.getValueIsAdjusting()) {
				int val = source.getValue();
				int snapped = Math.round(val / (float) minorTickSpacing) * minorTickSpacing;
				if (val != snapped) source.setValue(snapped);
				delaySliderValue.setText("Delay = " + source.getValue() + "ms");
			}
		});

		algorithmDropdown = new JComboBox<>();
		algorithmDropdown.addItem(new BFS(maze, mazePanel, delaySlider.getValue(), frame));
		algorithmDropdown.addItem(new DFS(maze, mazePanel, delaySlider.getValue(), frame));
		algorithmDropdown.addItem(new Dijkstra(maze, mazePanel, delaySlider.getValue(), frame));
		algorithmDropdown.addItem(new AStar(maze, mazePanel, delaySlider.getValue(), frame));
		algorithmDropdown.setRenderer(new DefaultListCellRenderer() {
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				if (value instanceof SearchAlgorithm search) {
					value = search.getName();
				}
				return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			}
		});
		algorithmDropdown.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				SearchAlgorithm newSelection = (SearchAlgorithm) e.getItem();
				if (currentAlgorithm != null && currentAlgorithm != newSelection) {
					currentAlgorithm.stop();
				}
				currentAlgorithm = newSelection;
			}
		});
		currentAlgorithm = algorithmDropdown.getItemAt(0);

		openStylesButton = new JButton("Open Generator Styling");
		openStylesButton.addActionListener(e -> {
			SearchAlgorithm selected = (SearchAlgorithm) algorithmDropdown.getSelectedItem();
			if (selected == null) return;

			selected.getDialogBox().setVisible(true);
		});

		startButton = new JButton("Search");
		startButton.addActionListener(e -> {
			if (currentAlgorithm == null) return;
			currentAlgorithm.setDelay(delaySlider.getValue());
			if (delaySlider.getValue() != 0) {
				animationStateButton.setEnabled(true);
				animationStateButton.setText("Stop Animation");
			} else {
				animationStateButton.setEnabled(false);
				animationStateButton.setText("-");
			}

			currentAlgorithm.start();
			frame.repaint();
			maze.hasGenerated(true);
		});
		activeOnMazeGen.add(startButton);

		animationStateButton = new JButton("-");
		animationStateButton.setEnabled(false);
		animationStateButton.addActionListener(e -> {
			if (currentAlgorithm == null) return;
			boolean animationStopped = currentAlgorithm.toggle();
			if (animationStopped) animationStateButton.setText("Start Animation");
			else animationStateButton.setText("Stop Animation");
			frame.repaint();
		});

		int gridy = 0;
		gbc.gridy = gridy++;
		gbc.insets = new Insets(5, 10, 0, 10);
		add(new JLabel("Algorithm:"), gbc);

		gbc.gridy = gridy++;
		add(algorithmDropdown, gbc);

		gbc.gridy = gridy++;
		gbc.insets = new Insets(5, 10, 10, 10);
		add(openStylesButton, gbc);

		gbc.gridy = gridy++;
		gbc.insets = new Insets(5, 10, 0, 10);
		add(new JLabel("Delay for generation:"), gbc);

		gbc.gridy = gridy++;
		add(delaySlider, gbc);

		gbc.gridy = gridy++;
		gbc.insets = new Insets(5, 10, 10, 10);
		add(delaySliderValue, gbc);

		gbc.insets = new Insets(5, 10, 0, 10);
		gbc.gridy = gridy++;
		add(startButton, gbc);

		gbc.insets = new Insets(5, 10, 10, 10);
		gbc.gridy = gridy++;
		add(animationStateButton, gbc);

		gbc.gridy = gridy++;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.VERTICAL;
		add(Box.createVerticalGlue(), gbc);
	}
}