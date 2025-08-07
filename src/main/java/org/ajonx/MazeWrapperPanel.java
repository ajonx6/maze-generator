package org.ajonx;

import javax.swing.*;

public class MazeWrapperPanel extends JPanel {
    private final MazePanel mazePanel;

    public MazeWrapperPanel(MazePanel mazePanel) {
        this.mazePanel = mazePanel;
        setLayout(null);
        add(mazePanel);
    }

    public void doLayout() {
        int size = Math.min(getWidth(), getHeight());
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;
        mazePanel.setBounds(x, y, size, size);
    }
}
