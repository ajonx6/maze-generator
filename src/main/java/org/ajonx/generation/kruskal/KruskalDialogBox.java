package org.ajonx.generation.kruskal;

import org.ajonx.ColorPreviewBox;
import org.ajonx.generation.MazeGeneratorDialogBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class KruskalDialogBox extends MazeGeneratorDialogBox {
	private JPanel mainPanel;
	private KruskalStyles styles;

	public KruskalDialogBox(Frame owner, KruskalStyles styles) {
		super(owner, "Kruskal");
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
		ColorPreviewRow colorRow1 = createColorRow("Breaking wall cell floor:", styles.getWillConnectCellFloor(), c -> styles.setWillConnectCellFloor(c.getRGB()));
		mainPanel.add(colorRow1.panel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		ColorPreviewRow colorRow2 = createColorRow("Breaking wall cell wall:", styles.getWillConnectCellWall(), c -> styles.setWillConnectCellWall(c.getRGB()));
		mainPanel.add(colorRow2.panel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		ColorPreviewRow colorRow3 = createColorRow("Rejecting wall cell floor:", styles.getWontConnectCellFloor(), c -> styles.setWontConnectCellFloor(c.getRGB()));
		mainPanel.add(colorRow3.panel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		ColorPreviewRow colorRow4 = createColorRow("Rejecting wall cell wall:", styles.getWontConnectCellWall(), c -> styles.setWontConnectCellWall(c.getRGB()));
		mainPanel.add(colorRow4.panel, gbc);

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