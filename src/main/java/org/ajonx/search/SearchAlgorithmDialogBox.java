package org.ajonx.search;

import javax.swing.*;
import java.awt.*;

public abstract class SearchAlgorithmDialogBox extends JDialog {
	protected final Frame owner;
	protected JPanel buttonsPanel;

	public SearchAlgorithmDialogBox(Frame owner, String title) {
		super(owner, title, true);
		this.owner = owner;
	}

	protected void createButtonUI() {
		buttonsPanel = new JPanel(new GridLayout(1, 3, 10, 0));
		buttonsPanel.add(new JButton("Close"));
		buttonsPanel.add(new JButton("Save"));
		buttonsPanel.add(new JButton("Apply"));
		add(buttonsPanel, BorderLayout.SOUTH);
	}

	protected void completeUI() {
		pack();
		setLocationRelativeTo(owner);
	}

	public abstract void createUI();
}