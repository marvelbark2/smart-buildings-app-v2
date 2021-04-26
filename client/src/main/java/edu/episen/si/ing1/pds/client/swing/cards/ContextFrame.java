package edu.episen.si.ing1.pds.client.swing.cards;

import edu.episen.si.ing1.pds.client.swing.global.Main;
import edu.episen.si.ing1.pds.client.swing.global.Navigate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class ContextFrame implements Navigate {
    Main app;
    Map<JButton, Routes> frames = new HashMap<>();

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
            app.getContext().removeAll();
            goTo.launch(this);
            app.getContext().invalidate();
            app.getContext().validate();
            app.getContext().repaint();
            app.getContext().setVisible(true);

        }
    }

    public void returnHere() {
        app.getContext().invalidate();
        app.getContext().validate();
        app.getContext().repaint();
        start();
    }

    @Override
    public void start() {
        JPanel context = app.getContext();
        context.setBackground(new Color(90, 64, 149));
        context.setLayout(new BorderLayout());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton cardTest = new JButton("Manipuler les cartes");
        JButton userTest = new JButton("Manipuler les users");
        JButton roleTest = new JButton("Manipuler les roles");
        JButton permissionTest = new JButton("Tester les cartes");

        buttons.setBackground(context.getBackground());
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

        context.add(buttons, BorderLayout.CENTER);
    }

    public JFrame frame() {
        return app.getFrame();
    }
}
