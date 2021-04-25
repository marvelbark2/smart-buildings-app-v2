package edu.episen.si.ing1.pds.client.test.swing;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Global {
    private JPanel panel;
    private JPanel menu;
    private JPanel context;

    public JPanel getMenu() {
        return menu;
    }

    public JPanel getContext() {
        return context;
    }

    public Global() {
        panel = new JPanel(new BorderLayout());
        panel.add(menu(), BorderLayout.WEST);
        panel.add(content());
    }

    public JPanel getPanel() {
        return panel;
    }

    private JPanel menu() {
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.add(Box.createVerticalStrut(100));

        JButton realize = new JButton("Realiser une location");
        realize.setMinimumSize(new Dimension(Integer.MAX_VALUE, 75));
        realize.setPreferredSize(new Dimension(Integer.MAX_VALUE, 75));
        realize.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
        realize.setOpaque(false);
        realize.setBackground(new Color(54, 38, 90));
        realize.setForeground(Color.white);
        realize.setBorderPainted(false);
        realize.setFocusPainted(false);

        JButton consult = new JButton("Consulter une location");
        consult.setMinimumSize(new Dimension(Integer.MAX_VALUE, 75));
        consult.setPreferredSize(new Dimension(Integer.MAX_VALUE, 75));
        consult.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
        consult.setOpaque(false);
        Global global = this;
        consult.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                new Window(global);
                panel.invalidate();
                panel.validate();
                panel.repaint();
            }
        });

        consult.setBackground(new Color(145, 115, 207));
        consult.setForeground(Color.white);
        consult.setBorderPainted(false);
        consult.setFocusPainted(false);

        JButton staff = new JButton("Personnel");
        staff.setMinimumSize(new Dimension(Integer.MAX_VALUE, 75));
        staff.setPreferredSize(new Dimension(Integer.MAX_VALUE, 75));
        staff.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
        staff.setOpaque(true);

        JPanel underMenu = new JPanel(new FlowLayout(FlowLayout.LEFT));
        underMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        underMenu.setOpaque(true);

        JButton disconnect = new JButton("Deconnecter");
        disconnect.setMaximumSize(new Dimension(100, 100));
        disconnect.setOpaque(true);

        ImageIcon iconHome = new ImageIcon(new ImageIcon("C:\\Users\\cedri\\Bureau\\pds\\image\\maison.png").getImage().getScaledInstance(18,18,Image.SCALE_DEFAULT));
        JButton home = new JButton(iconHome);
        home.setOpaque(true);

        ImageIcon iconRefresh = new ImageIcon(new ImageIcon("C:\\Users\\cedri\\Bureau\\pds\\image\\actualiser.png").getImage().getScaledInstance(18,18,Image.SCALE_DEFAULT));
        JButton refresh = new JButton(iconRefresh);
        refresh.setOpaque(true);
        realize.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        underMenu.add(disconnect);
        underMenu.add(home);
        underMenu.add(refresh);

        menu.add(realize);
        menu.add(consult);
        menu.add(staff);
        menu.add(Box.createGlue());
        menu.add(underMenu);

        menu.setBackground(new Color(54, 38, 90));
        menu.setOpaque(true);
        menu.setPreferredSize(new Dimension(250, 800));

        this.menu = menu;
        return menu;
    }

    private JPanel content() {
        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(Color.RED));
        return panel;
    }

    public void setWelcome() {
        panel.add(menu(), BorderLayout.WEST);
        panel.add(content());
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Smart building");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(1250, 800));
        frame.setVisible(true);
        frame.setPreferredSize(frame.getSize());

        Global global = new Global();
        global.setWelcome();
        frame.setContentPane(global.getPanel());

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public void returnBack() {
        panel.removeAll();
        setWelcome();
        panel.invalidate();
        panel.validate();
        panel.repaint();
    }
}
