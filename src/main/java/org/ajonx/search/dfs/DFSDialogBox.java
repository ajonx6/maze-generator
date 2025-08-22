package org.ajonx.search.dfs;

import org.ajonx.StylesDialogBox;
import org.ajonx.ui.ColorPreviewBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class DFSDialogBox extends StylesDialogBox {
	private JPanel mainPanel;
	private DFSStyles styles;

	public DFSDialogBox(Frame owner, DFSStyles styles) {
		super(owner, "DFS");
		this.styles = styles;
		createUI();
	}


	public void createUI() {
		setLayout(new BorderLayout());

		mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		ColorPreviewRow colorRow1 = createColorRow("Current cell floor:", styles.getCurrentCellFloor(), c -> styles.setCurrentCellFloor(c.getRGB()));
		mainPanel.add(colorRow1.panel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		ColorPreviewRow colorRow2 = createColorRow("Current cell wall:", styles.getCurrentCellWall(), c -> styles.setCurrentCellWall(c.getRGB()));
		mainPanel.add(colorRow2.panel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		ColorPreviewRow colorRow3 = createColorRow("Queued cell floor:", styles.getQueuedCellFloor(), c -> styles.setQueuedCellFloor(c.getRGB()));
		mainPanel.add(colorRow3.panel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		ColorPreviewRow colorRow4 = createColorRow("Queued cell wall:", styles.getQueuedCellWall(), c -> styles.setQueuedCellWall(c.getRGB()));
		mainPanel.add(colorRow4.panel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		ColorPreviewRow colorRow5 = createColorRow("Seen cell floor:", styles.getSeenCellFloor(), c -> styles.setSeenCellFloor(c.getRGB()));
		mainPanel.add(colorRow5.panel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		ColorPreviewRow colorRow6 = createColorRow("Seen cell wall:", styles.getSeenCellWall(), c -> styles.setSeenCellWall(c.getRGB()));
		mainPanel.add(colorRow6.panel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		ColorPreviewRow colorRow7 = createColorRow("Path cell floor:", styles.getPathCellFloor(), c -> styles.setPathCellFloor(c.getRGB()));
		mainPanel.add(colorRow7.panel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		ColorPreviewRow colorRow8 = createColorRow("Path cell wall:", styles.getPathCellWall(), c -> styles.setPathCellWall(c.getRGB()));
		mainPanel.add(colorRow8.panel, gbc);

		add(mainPanel, BorderLayout.CENTER);

		createButtonUI();
		completeUI();
	}

	private static class ColorPreviewRow {
		JPanel panel;
		ColorPreviewBox preview;

		public ColorPreviewRow(JPanel panel, ColorPreviewBox preview) {
			this.panel = panel;
			this.preview = preview;
		}
	}

	private ColorPreviewRow createColorRow(String title, int color, Consumer<Color> onColorChange) {
		JPanel colorRow = new JPanel(new GridBagLayout());
		colorRow.setOpaque(false);

		JLabel label = new JLabel(title);
		Color initialColor = new Color(color);
		ColorPreviewBox cpb = new ColorPreviewBox(initialColor);

		cpb.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Color chosen = JColorChooser.showDialog(cpb, "Choose a color", cpb.getColor());
				if (chosen != null) {
					cpb.setColor(chosen);
					onColorChange.accept(chosen);
				}
			}
		});

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 20);
		colorRow.add(label, gbc);

		gbc.gridx = 1;
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.insets = new Insets(0, 0, 0, 10);
		colorRow.add(cpb, gbc);

		return new ColorPreviewRow(colorRow, cpb);
	}
}