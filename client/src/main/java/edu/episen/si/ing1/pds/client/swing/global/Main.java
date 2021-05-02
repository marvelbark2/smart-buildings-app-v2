package edu.episen.si.ing1.pds.client.swing.global;

import edu.episen.si.ing1.pds.client.swing.cards.ContextFrame;
import edu.episen.si.ing1.pds.client.swing.global.shared.Ui;
import edu.episen.si.ing1.pds.client.swing.location.LocationMenu;
import edu.episen.si.ing1.pds.client.swing.mapping.selim.*;
import edu.episen.si.ing1.pds.client.utils.Utils;

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
        if(Utils.isGuestPage()) {
            loadCompanyWindows();
        } else {
            loadSystemWindow();
        }

    }

    public void loadCompanyWindows() {
        frame.setSize(800, 400);
        frame.setPreferredSize(frame.getSize());
        global.add(new CompanyFrame(this));
        frame.setResizable(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width / 2 - frame.getWidth() / 2, screenSize.height / 2 - frame.getHeight() / 2);
        global.invalidate();
        global.validate();
        global.repaint();
        frame.pack();
    }

    public void loadSystemWindow() {
        frame.setResizable(true);
        frame.setSize(1800, 1000);

        frame.setPreferredSize(frame.getSize());

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width / 2 - frame.getWidth() / 2, screenSize.height / 2 - frame.getHeight() / 2);

        frames = new HashMap<>();

        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(5);
        global.setLayout(borderLayout);

        setupFrame();

        global.invalidate();
        global.validate();
        global.repaint();

        frame.pack();
    }

    public void setupFrame() {
        global.removeAll();

        JPanel header = new JPanel(new BorderLayout());

        JPanel logo = new JPanel();
        JLabel label = new JLabel("Logo", SwingUtilities.CENTER);
        header.setBackground(new Color(54, 38, 90));
        label.setForeground(Color.white);
        logo.add(label);
        logo.setOpaque(false);

        JPanel leftPanel = new JPanel();
        JLabel disconnect = new JLabel("Deconnecté", SwingUtilities.CENTER);
        disconnect.setSize(100, 75);
        disconnect.setMinimumSize(disconnect.getSize());
        disconnect.setPreferredSize(disconnect.getSize());
        disconnect.setMaximumSize(disconnect.getSize());
        disconnect.setOpaque(true);
        disconnect.setBackground(new Color(72, 64, 92));
        disconnect.setForeground(Color.white);
        disconnect.setFont(Ui.FONT_GENERAL_UI);
        disconnect.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Utils.logOut();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                frame.getContentPane().removeAll();
                loadCompanyWindows();
                frame.invalidate();
                frame.validate();
                frame.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

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
        });

        leftPanel.add(disconnect);
        leftPanel.setOpaque(false);

        header.add(leftPanel, BorderLayout.WEST);
        header.add(logo, BorderLayout.CENTER);

        global.add(header, BorderLayout.PAGE_START);
        setupMenu();
        global.add(menu, BorderLayout.WEST);
        setupContext();
        global.add(context, BorderLayout.CENTER);
        setupBloc();
        global.add(bloc, BorderLayout.EAST);

        global.setBackground(Color.white);
    }

    private void setupMenu() {
        menu = new JPanel();
        menu.setBorder(new LineBorder(Color.BLACK));
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.add(Box.createVerticalStrut(100));

        JLabel realize = new MenuItem("Realiser une location");
        frames.put(realize, new LocationMenu(this));
        realize.addMouseListener(this);

        JLabel consult = new MenuItem("Consulter une location");
        frames.put(consult, new Mapping_Window(this));
        consult.addMouseListener(this);

        JLabel staff = new MenuItem("Configurer les cartes d'acces");
        staff.addMouseListener(this);
        frames.put(staff, new ContextFrame(this));

        menu.add(realize);
        menu.add(consult);
        menu.add(staff);
        menu.add(Box.createGlue());

        menu.setBackground(new Color(54, 38, 90));
        menu.setOpaque(true);
        menu.setPreferredSize(new Dimension(250, 800));
    }

    private void setupContext() {
        context = new JPanel();
        context.setOpaque(false);
    }
    private void setupBloc() {
        bloc = new JPanel();
        bloc.setBorder(new LineBorder(Color.RED));
        bloc.setVisible(false);
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
