package edu.episen.si.ing1.pds.client.swing.global;

import edu.episen.si.ing1.pds.client.swing.global.shared.Ui;

import javax.swing.*;
import java.awt.*;

public class MenuItem extends JLabel {
    public MenuItem(String title) {
        super(title, SwingUtilities.CENTER);
        setMinimumSize(new Dimension(Integer.MAX_VALUE, 75));
        setPreferredSize(new Dimension(Integer.MAX_VALUE, 75));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
        setBackground(new Color(54, 38, 90));
        setForeground(Color.white);
        setFont(Ui.FONT_GENERAL_UI);
        setOpaque(true);
    }
}
