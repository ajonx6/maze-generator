package org.ajonx;

import org.ajonx.generation.MazeGenerator;
import org.ajonx.generation.dfs.DepthFirstSearch;
import org.ajonx.generation.kruskal.Kruskal;
import org.ajonx.generation.prims.Prims;
import org.ajonx.search.SearchAlgorithm;
import org.ajonx.search.bfs.BFS;
import org.ajonx.search.dfs.DFS;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ItemEvent;

public class ControlPanel extends JPanel {
	private final Maze maze;
	private final JFrame frame;
	private final MazePanel mazePanel;
	private MazeGenerator currentGenerator;

	private JLabel generatorTitle;
	private JComboBox<MazeGenerator> generatorDropdown;
	private JButton openStylesButton;
	private JTextField mazeWidthInput;
	private JTextField mazeHeightInput;
	private JSlider delaySlider;
	private JLabel delaySliderValue;
	private JTextField seedInput;
	private JLabel seedLabel;
	private JButton startButton;
	private JButton animationStateButton;

	private JLabel searchTitle;
	public JButton searchBFS;
	public JButton searchDFS;

	private final DocumentFilter digitsOnlyFilter = new DocumentFilter() {
		@Override
		public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
			if (string.matches("\\d+")) {
				super.insertString(fb, offset, string, attr);
			}
		}

		@Override
		public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			if (text.matches("\\d+")) {
				super.replace(fb, offset, length, text, attrs);
			}
		}
	};

	public ControlPanel(Maze maze, JFrame frame, MazePanel mazePanel) {
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

		generatorTitle = new JLabel("Generator Settings");
		generatorTitle.setFont(generatorTitle.getFont().deriveFont(Font.BOLD));

		mazeWidthInput = new JTextField("50", 3);
		((AbstractDocument) mazeWidthInput.getDocument()).setDocumentFilter(digitsOnlyFilter);

		mazeHeightInput = new JTextField("50", 3);
		((AbstractDocument) mazeHeightInput.getDocument()).setDocumentFilter(digitsOnlyFilter);

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

		generatorDropdown = new JComboBox<>();
		generatorDropdown.addItem(new DepthFirstSearch(maze, mazePanel, delaySlider.getValue(), frame));
		generatorDropdown.addItem(new Prims(maze, mazePanel, delaySlider.getValue(), frame));
		generatorDropdown.addItem(new Kruskal(maze, mazePanel, delaySlider.getValue(), frame));
		generatorDropdown.setRenderer(new DefaultListCellRenderer() {
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				if (value instanceof MazeGenerator gen) {
					value = gen.getName();
				}
				return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			}
		});
		generatorDropdown.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				MazeGenerator newSelection = (MazeGenerator) e.getItem();
				if (currentGenerator != null && currentGenerator != newSelection) {
					currentGenerator.stop();
				}
				currentGenerator = newSelection;
			}
		});
		currentGenerator = generatorDropdown.getItemAt(0);

		openStylesButton = new JButton("Open Generator Styling");
		openStylesButton.addActionListener(e -> {
			MazeGenerator selected = (MazeGenerator) generatorDropdown.getSelectedItem();
			if (selected == null) return;

			selected.getDialogBox().setVisible(true);
		});

		seedLabel = new JLabel("Seed");

		seedInput = new JTextField("", 12);
		((AbstractDocument) seedInput.getDocument()).setDocumentFilter(digitsOnlyFilter);

		startButton = new JButton("Generate Maze");
		startButton.addActionListener(e -> {
			if (currentGenerator == null || mazeWidthInput.getText().isEmpty() || mazeHeightInput.getText().isEmpty()) return;

			maze.setWidth(Integer.parseInt(mazeWidthInput.getText()));
			maze.setHeight(Integer.parseInt(mazeHeightInput.getText()));
			currentGenerator.setDelay(delaySlider.getValue());
			if (delaySlider.getValue() != 0) {
				animationStateButton.setEnabled(true);
				animationStateButton.setText("Stop Animation");
			} else {
				animationStateButton.setEnabled(false);
				animationStateButton.setText("-");
			}

			int seed = currentGenerator.start(seedInput.getText().isEmpty() ? -1 : Integer.parseInt(seedInput.getText()));
			if (seed != -1) seedLabel.setText("Seed (previous: " + seed + ")");

			frame.repaint();
			maze.hasGenerated(true);
		});

		animationStateButton = new JButton("-");
		animationStateButton.setEnabled(false);
		animationStateButton.addActionListener(e -> {
			if (currentGenerator == null || mazeWidthInput.getText().isEmpty() || mazeHeightInput.getText().isEmpty()) return;
			boolean animationStopped = currentGenerator.toggle();
			if (animationStopped) animationStateButton.setText("Start Animation");
			else animationStateButton.setText("Stop Animation");
			frame.repaint();
		});

		searchTitle = new JLabel("Search Settings");
		searchTitle.setFont(searchTitle.getFont().deriveFont(Font.BOLD));

		searchBFS = new JButton("Search BFS");
		searchBFS.addActionListener(e -> {
			BFS bfs = new BFS(maze, mazePanel, delaySlider.getValue());
			frame.repaint();
			bfs.start(-1);
		});

		searchDFS = new JButton("Search DFS");
		searchDFS.addActionListener(e -> {
			DFS dfs = new DFS(maze, mazePanel, delaySlider.getValue());
			frame.repaint();
			dfs.start(-1);
		});

		int gridy = 0;
		gbc.gridy = gridy++;
		gbc.insets = new Insets(5, 10, 0, 10);
		add(generatorTitle, gbc);

		gbc.gridy = gridy++;
		add(new JLabel("Algorithm:"), gbc);

		gbc.gridy = gridy++;
		add(generatorDropdown, gbc);

		gbc.gridy = gridy++;
		gbc.insets = new Insets(5, 10, 10, 10);
		add(openStylesButton, gbc);

		gbc.gridy = gridy++;
		gbc.insets = new Insets(5, 10, 0, 10);
		add(new JLabel("Width:"), gbc);

		gbc.gridy = gridy++;
		add(mazeWidthInput, gbc);

		gbc.gridy = gridy++;
		add(new JLabel("Height:"), gbc);

		gbc.gridy = gridy++;
		gbc.insets = new Insets(5, 10, 10, 10);
		add(mazeHeightInput, gbc);

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
		add(seedLabel, gbc);

		gbc.gridy = gridy++;
		add(seedInput, gbc);

		gbc.gridy = gridy++;
		add(startButton, gbc);

		gbc.insets = new Insets(5, 10, 20, 10);
		gbc.gridy = gridy++;
		add(animationStateButton, gbc);

		gbc.gridy = gridy++;
		gbc.insets = new Insets(5, 10, 0, 10);
		add(searchTitle, gbc);

		gbc.gridy = gridy++;
		add(searchBFS, gbc);

		gbc.gridy = gridy++;
		add(searchDFS, gbc);

		gbc.gridy = gridy++;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.VERTICAL;
		add(Box.createVerticalGlue(), gbc);
	}
}