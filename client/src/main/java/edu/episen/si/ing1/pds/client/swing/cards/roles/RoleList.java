package edu.episen.si.ing1.pds.client.swing.cards.roles;

import edu.episen.si.ing1.pds.client.swing.cards.ContextFrame;
import edu.episen.si.ing1.pds.client.swing.cards.Routes;

import javax.swing.*;
import java.awt.*;

public class RoleList implements Routes {
    @Override
    public void launch(ContextFrame context) {
        JPanel body = context.getApp().getContext();

        JPanel panel = new JPanel(new GridLayout(2, 2, 50, 50));

        panel.add(new JLabel("Role Index Page"));

        body.add(panel);
        body.setVisible(true);
    }
}
