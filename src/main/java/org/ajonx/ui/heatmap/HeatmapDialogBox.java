package org.ajonx.ui.heatmap;

import org.ajonx.StylesDialogBox;
import org.ajonx.ui.ColorPreviewBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class HeatmapDialogBox extends StylesDialogBox {
	private JPanel mainPanel;
	private HeatmapStyles styles;

	public HeatmapDialogBox(Frame owner, HeatmapStyles styles) {
		super(owner, "Heatmap");
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
		ColorPreviewRow colorRow1 = createColorRow("Close color:", styles.getCloseColor(), c -> styles.setCloseColor(c.getRGB()));
		mainPanel.add(colorRow1.panel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		ColorPreviewRow colorRow2 = createColorRow("Mid color:", styles.getMidColor(), c -> styles.setMidColor(c.getRGB()));
		mainPanel.add(colorRow2.panel, gbc);

		gbc.gridx = 2;
		gbc.gridy = 0;
		ColorPreviewRow colorRow3 = createColorRow("Far color:", styles.getFarColor(), c -> styles.setFarColor(c.getRGB()));
		mainPanel.add(colorRow3.panel, gbc);

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