package edu.episen.si.ing1.pds.client.test.swing.cards;

import edu.episen.si.ing1.pds.client.test.swing.Routes;
import edu.episen.si.ing1.pds.client.test.swing.global.Main;
import edu.episen.si.ing1.pds.client.test.swing.global.Navigate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ContextFrame implements Navigate {
    Main app;
    Map<JButton, Routes> frames = new HashMap<>();
    Routes currentPane = null;

    public ContextFrame(Main app) {
        this.app = app;
    }

    public Main getApp() {
        return app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton) {
            JButton clickedButton = (JButton) e.getSource();
            Routes goTo = frames.get(clickedButton);
            currentPane = goTo;

            app.getContext().removeAll();
            goTo.launch(this);
            app.getContext().invalidate();
            app.getContext().validate();
            app.getContext().repaint();
            app.getContext().setVisible(true);

        }
    }

//    private void run() {
//
//        JPanel context = new JPanel();
//        context.setBackground(new Color(237, 250, 252));
//        context.setPreferredSize(frame.getSize());
//
//        JPanel buttons = new JPanel(new GridLayout(2, 2, 60, 60));
//        JButton cardTest = new JButton("Manipuler les cartes");
//        JButton userTest = new JButton("Manipuler les users");
//        JButton roleTest = new JButton("Manipuler les roles");
//        JButton permissionTest = new JButton("Tester les cartes");
//
//        userTest.addActionListener(this);
//        cardTest.addActionListener(this);
//        roleTest.addActionListener(this);
//
//        frames.put(cardTest, new CardView());
//        frames.put(roleTest, new CardRoleTest());
//        frames.put(userTest, new UsersView());
//
//        buttons.add(cardTest);
//        buttons.add(roleTest);
//        buttons.add(userTest);
//        buttons.add(permissionTest);
//
//        context.add(buttons);
//
//        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
//        frame.getContentPane().add(context);
//        frame.setVisible(true);
//        frame.pack();
//    }

    public void returnHere() {
        app.getContext().invalidate();
        app.getContext().validate();
        app.getContext().repaint();
        start();
    }

//    public JFrame getFrame() {
//        return frame;
//    }

    @Override
    public void start() {
        JPanel context = app.getContext();
        context.setBackground(new Color(90, 64, 149));
//        context.setPreferredSize(frame.getSize());

        JPanel buttons = new JPanel(new GridLayout(2, 2, 60, 60));
        JButton cardTest = new JButton("Manipuler les cartes");
        JButton userTest = new JButton("Manipuler les users");
        JButton roleTest = new JButton("Manipuler les roles");
        JButton permissionTest = new JButton("Tester les cartes");

        userTest.addActionListener(this);
        cardTest.addActionListener(this);
        roleTest.addActionListener(this);

        frames.put(cardTest, new CardView());
        frames.put(roleTest, new CardRoleTest());
        frames.put(userTest, new UsersView());

        buttons.add(cardTest);
        buttons.add(roleTest);
        buttons.add(userTest);
        buttons.add(permissionTest);

        context.add(buttons);
    }
}
