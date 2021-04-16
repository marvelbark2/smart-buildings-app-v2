package edu.episen.si.ing1.pds.client.test.swing.cards;

import edu.episen.si.ing1.pds.client.test.swing.Routes;

import javax.swing.*;
import java.awt.*;

public class CardRoleTest implements Routes {
    public CardRoleTest() {

    }

    @Override
    public void launch(ContextFrame context) {
        JFrame frame = context.getFrame();
        frame.setTitle("Tester Permission");

        JPanel panel = new JPanel();
        JTextField serialField = new JTextField(25);
        String[] desks = { "Salle 1", "Salle 2", "Salle 3", "Salle 4" };
        DefaultComboBoxModel model = new DefaultComboBoxModel(desks);
        JComboBox comboBox = new JComboBox(model);

        JButton jButton = new JButton("Verifier");

        panel.add(serialField);
        panel.add(comboBox);
        panel.add(jButton);

        frame.add(panel);
        frame.setVisible(true);
        frame.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }
}
