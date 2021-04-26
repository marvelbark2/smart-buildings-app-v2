package edu.episen.si.ing1.pds.client.swing.global;

import edu.episen.si.ing1.pds.client.swing.Window;
import edu.episen.si.ing1.pds.client.swing.cards.ContextFrame;
import edu.episen.si.ing1.pds.client.swing.location.LocationMenu;
import edu.episen.si.ing1.pds.client.swing.shared.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class Main implements MouseListener {
    private final JPanel global;
    private JPanel menu;
    private JPanel context;
    private JPanel bloc;
    private final JFrame frame;



    private Map<JLabel, Navigate> frames;
    public Main() {
        frame = new JFrame("Smart building app");
        global = new JPanel();
        frames = new HashMap<>();
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(5);
        global.setLayout(borderLayout);
        setupFrame();


    }

    public void setupFrame() {
        global.removeAll();
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

        global.invalidate();
        global.validate();
        global.repaint();
        frame.pack();
    }

    private void setupMenu() {
        menu = new JPanel();
        menu.setBorder(new LineBorder(Color.BLACK));
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.add(Box.createVerticalStrut(100));

        JLabel realize = new JLabel("Realiser une location", SwingUtilities.CENTER);
        realize.setMinimumSize(new Dimension(Integer.MAX_VALUE, 75));
        realize.setPreferredSize(new Dimension(Integer.MAX_VALUE, 75));
        realize.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
        realize.setOpaque(true);
        realize.setBackground(new Color(54, 38, 90));
        realize.setForeground(Color.white);
        frames.put(realize, new LocationMenu(this));
        realize.addMouseListener(this);

        JLabel consult = new JLabel("Consulter une location", SwingUtilities.CENTER);
        consult.setMinimumSize(new Dimension(Integer.MAX_VALUE, 75));
        consult.setPreferredSize(new Dimension(Integer.MAX_VALUE, 75));
        consult.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
        consult.setOpaque(true);
        frames.put(consult, new Window(this));
        consult.addMouseListener(this);
        consult.setBackground(new Color(54, 38, 90));
        consult.setForeground(Color.white);

        JLabel staff = new JLabel("Configurer les cartes d'acces", SwingUtilities.CENTER);
        staff.setMinimumSize(new Dimension(Integer.MAX_VALUE, 75));
        staff.setPreferredSize(new Dimension(Integer.MAX_VALUE, 75));
        staff.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
        staff.setBackground(new Color(54, 38, 90));
        staff.setForeground(Color.white);

        staff.setOpaque(true);

        staff.addMouseListener(this);
        frames.put(staff, new ContextFrame(this));

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
        context.setBackground(Ui.COLOR_INTERACTIVE);
    }
    private void setupBloc() {
        bloc = new JPanel();
        bloc.setBorder(new LineBorder(Color.RED));
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

    public JFrame getFrame() {
        return frame;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() instanceof JLabel) {
            context.removeAll();
            JLabel clickedButton = (JLabel) e.getSource();
            clickedButton.setBackground(new Color(72, 64, 92));

            Navigate goTo = frames.get(clickedButton);
            goTo.start();
            context.invalidate();
            context.validate();
            context.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        JLabel label = (JLabel) e.getSource();
        label.setBackground(new Color(72, 64, 92));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (JLabel l: frames.keySet()) {
            l.setBackground(new Color(54, 38, 90));
            l.repaint();
        }
        JLabel label = (JLabel) e.getSource();
        label.setBackground(new Color(72, 64, 92));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JLabel label = (JLabel) e.getSource();
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JLabel label = (JLabel) e.getSource();
        label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
}
