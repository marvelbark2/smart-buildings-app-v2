package edu.episen.si.ing1.pds.client.test.swing.cards;

import edu.episen.si.ing1.pds.client.test.swing.Routes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ContextFrame implements ActionListener {
    JFrame frame = new JFrame();
    Map<JButton, Routes> frames = new HashMap<>();

    public ContextFrame() {
        frame.setTitle("Systeme des cartes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        run();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton) {
            frame.getContentPane().removeAll();
            JButton clickedButton = (JButton) e.getSource();
            Routes goTo = frames.get(clickedButton);
            goTo.launch(this);
            frame.pack();
        }
    }

    private void run() {

        JPanel context = new JPanel();
        context.setBackground(new Color(237, 250, 252));
        context.setPreferredSize(frame.getSize());

        JPanel buttons = new JPanel();
        JButton cardTest = new JButton("Manipuler les cartes");
        JButton roleTest = new JButton("Tester les cartes");

        cardTest.addActionListener(this);
        roleTest.addActionListener(this);

        frames.put(cardTest, new CardView());
        frames.put(roleTest, new CardRoleTest());

        buttons.add(cardTest);
        buttons.add(roleTest);

        context.add(buttons);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.getContentPane().add(context);
        frame.setVisible(true);
        frame.pack();
    }

    public void returnHere() {
        frame.getContentPane().removeAll();
        frame.getContentPane().validate();
        frame.getContentPane().repaint();
        run();
    }

    public JFrame getFrame() {
        return frame;
    }

}
