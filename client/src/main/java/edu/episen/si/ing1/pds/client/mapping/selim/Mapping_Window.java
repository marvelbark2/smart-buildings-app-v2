package edu.episen.si.ing1.pds.client.mapping.selim;

import edu.episen.si.ing1.pds.client.swing.global.Main;
import edu.episen.si.ing1.pds.client.swing.global.Navigate;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Mapping_Window implements Navigate {
    Main global;

    public Mapping_Window(Main global) {
        this.global = global;


    }

    private void menuScroll() {
        JPanel menuPanel = global.getMenu();
        JTree tree = new JTree();
        tree.setUI(new BasicTreeUI() {
            @Override
            public void setExpandedIcon(Icon newG) {
                super.setExpandedIcon(null);
            }

            @Override
            public void setCollapsedIcon(Icon newG) {
                super.setCollapsedIcon(null);
            }

            protected void paintHorizontalLine(Graphics g, JComponent c, int y, int left, int right){
                super.paintHorizontalLine(g,c,y,left,right);
            }
            protected void paintVerticalLine(Graphics g,JComponent c,int x,int top,int bottom){
                super.paintVerticalLine(g,c,x,top,bottom);
            }
        });
        JScrollPane menu = new JScrollPane(tree);

        menuPanel.removeAll();
        menu.setPreferredSize(new Dimension(200,0));

        menuPanel.add(menu);
        menuPanel.invalidate();
        menuPanel.validate();
        menuPanel.repaint();


    }

    private JToolBar createToolBar() {

        JToolBar toolBar = new JToolBar();

        JButton retour = new JButton("Retour");
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                global.setupFrame();
            }
        });
        toolBar.add(retour);

        return toolBar;
    }

    private JPanel carte() {

        JPanel carte = new JPanel(new GridLayout(3,3,10,10));
        carte.setBorder(new LineBorder(Color.black));

        JTextField emplacement1 = new JTextField("Deposer un équipement");
        emplacement1.setBorder(new LineBorder(Color.GREEN));
        carte.add(emplacement1);

        JTextField emplacement2 = new JTextField("Deposer un équipement");
        emplacement2.setBorder(new LineBorder(Color.GREEN));
        carte.add(emplacement2);

        JTextField emplacement3 = new JTextField("Deposer un équipement");
        emplacement3.setBorder(new LineBorder(Color.RED));
        carte.add(emplacement3);

        JTextField emplacement4 = new JTextField("Deposer un équipement");
        emplacement4.setBorder(new LineBorder(Color.GREEN));
        carte.add(emplacement4);

        JTextField emplacement5 = new JTextField("Deposer un équipement");
        emplacement5.setBorder(new LineBorder(Color.RED));
        carte.add(emplacement5);


        return carte;

    }

    private void bloc_equipement() {

        JPanel bloc = global.getBloc();
        bloc.setLayout(new GridLayout(3,1));

        JLabel ecran = new JLabel("Ecran");
        ecran.setIcon(new ImageIcon(new ImageIcon(getUriOfFile("icon/ecran.jpg")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        ecran.setPreferredSize(new Dimension(100,30));

        JLabel prise = new JLabel("Prise");
        prise.setPreferredSize(new Dimension(100,30));
        prise.setIcon(new ImageIcon(new ImageIcon(getUriOfFile("icon/prise.jpg")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));

        JLabel capteur = new JLabel("Capteur");
        capteur.setPreferredSize(new Dimension(100,30));
        capteur.setIcon(new ImageIcon(new ImageIcon(getUriOfFile("icon/capteur.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));

        bloc.add(ecran);
        bloc.add(capteur);
        bloc.add(prise);

        bloc.repaint();
    }

    private String getUriOfFile(String file) {
        String uri = null;
        try {
            uri = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(file)).getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uri;
    }

    @Override
    public void start() {
        JPanel contentPane = global.getContext();
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(20);
        borderLayout.setVgap(20);
        contentPane.setLayout(borderLayout);
        menuScroll();
        bloc_equipement();

        contentPane.add(carte());

        contentPane.add(createToolBar(), BorderLayout.SOUTH);
        global.getFrame().pack();


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
