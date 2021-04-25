package edu.episen.si.ing1.pds.client.test.swing.global;

import edu.episen.si.ing1.pds.client.network.SocketConfig;
import edu.episen.si.ing1.pds.client.test.swing.Global;
import edu.episen.si.ing1.pds.client.test.swing.cards.ContextFrame;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Main implements ActionListener {
    private final JPanel global;
    private JPanel menu;
    private JPanel context;
    private JPanel bloc;



    private Map<JButton, Navigate> frames;
    public Main() {
        global = new JPanel();
        frames = new HashMap<>();
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(5);
//        borderLayout.setVgap(20);
        global.setLayout(borderLayout);

        JPanel logo = new JPanel(new FlowLayout());
        JLabel label = new JLabel("Logo");
        logo.setBackground(new Color(54, 38, 90));
        label.setForeground(Color.white);
        logo.add(label, BorderLayout.CENTER);
        global.add(logo, BorderLayout.PAGE_START);
        setupMenu();
        global.add(menu, BorderLayout.WEST);
        setupContext();
        global.add(context, BorderLayout.CENTER);
        setupBloc();
        global.add(bloc, BorderLayout.EAST);

    }

    private void setupMenu() {
        menu = new JPanel();
        menu.setBorder(new LineBorder(Color.BLACK));
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

        consult.setBackground(new Color(145, 115, 207));
        consult.setForeground(Color.white);
        consult.setBorderPainted(false);
        consult.setFocusPainted(false);

        JButton staff = new JButton("Configurer les cartes d'acces");
        staff.setMinimumSize(new Dimension(Integer.MAX_VALUE, 75));
        staff.setPreferredSize(new Dimension(Integer.MAX_VALUE, 75));
        staff.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
        staff.setOpaque(true);
        frames.put(staff, new ContextFrame(this));
        staff.addActionListener(this);

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
    }

    private void setupContext() {
        context = new JPanel();
        context.setBorder(new LineBorder(Color.GREEN));
    }
    private void setupBloc() {
        bloc = new JPanel();
        bloc.setBorder(new LineBorder(Color.RED));
    }

    private static void createAndShowGUI() {
        SocketConfig.Instance.setEnv(true);
        Main app = new Main();
        JFrame frame = new JFrame("Smart building app");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 1000);
        frame.setPreferredSize(frame.getSize());
        frame.setVisible(true);
        frame.setContentPane(app.getGlobal());
        frame.pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
    public JPanel getGlobal() {
        return global;
    }

    public JPanel getMenu() {
        return menu;
    }

    public JPanel getContext() {
        return context;
    }

    public JPanel getBloc() {
        return bloc;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton) {
            context.removeAll();
            JButton clickedButton = (JButton) e.getSource();
            Navigate goTo = frames.get(clickedButton);
            goTo.start();
            context.invalidate();
            context.validate();
            context.repaint();
        }
    }
}
