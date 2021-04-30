package edu.episen.si.ing1.pds.client.swing.cards;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.network.SocketFacade;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class CardRoleTest implements Routes {

    @Override
    public void launch(ContextFrame context) {
        JPanel frame = context.getApp().getContext();

        JPanel panel = new JPanel(new GridLayout(3, 1, 100, 100));

        JPanel cardPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cardPanel.setOpaque(true);
        cardPanel.add(new JLabel("USER"));
        cardPanel.add(new JLabel("CARD"));
        JTextField serialField = new JTextField(25);
        cardPanel.add(serialField);

        JPanel workspacePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        workspacePanel.setOpaque(true);

        String[] buildings = { "Batiment 1", "Batiment 2", "Batiment 3", "Batiment 4" };
        JComboBox buldingsCombobox = new JComboBox(buildings);

        String[] florrs = { "Salle 1", "Salle 2", "Salle 3", "Salle 4" };
        JComboBox comboBox = new JComboBox(florrs);

        String[] desks = { "Salle 1", "Salle 2", "Salle 3", "Salle 4" };
        JComboBox comboBox2 = new JComboBox(desks);

        workspacePanel.add(buldingsCombobox);
        workspacePanel.add(comboBox);
        workspacePanel.add(comboBox2);

        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton jButton = new JButton("Verifier");
        submitPanel.setOpaque(true);
        submitPanel.add(jButton);

        panel.add(cardPanel);
        panel.add(workspacePanel);
        panel.add(submitPanel);

        frame.add(panel);
        frame.setVisible(true);
    }
}
