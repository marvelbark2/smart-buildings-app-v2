package edu.episen.si.ing1.pds.client.swing.ConfigFenetre;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class WindowConfig extends JDialog  {

    private final Logger logger = LoggerFactory.getLogger(WindowConfig.class.getName());
    private JRadioButton wClosed, wMOpened, wOpened, sClosed, sOpened, tAuto, tManual, tpFChoice, tpSChoice;
    private ButtonGroup wG, wT, wS;

    public WindowConfig(Frame frame, int positionID) {
        super(frame);
        this.setSize(1000, 1000);
        this.setPreferredSize(this.getSize());

        JPanel content = new JPanel(new BorderLayout(40, 40));
        JPanel header = new JPanel(new GridLayout(2,1,20,20));
        String[][] rows = {
                { "Etat de fenetre", "FermÃ©" },
                { "Store fermÃ©", "10%" },
                { " Fenetre teintÃ©", "10%" },
                { "T intÃ©rieur", "25 Â°C" },
                { "T extÃ©rieur", "25 Â°C" }
        };
        JTable table = new JTable(rows, new String[] { "-", "-" });
        header.add(table);
        header.add(new JButton("Actualiser"));

        content.add(header, BorderLayout.NORTH);

        JTabbedPane tp=new JTabbedPane();

        JPanel wTab = new JPanel();
        wTab.setLayout(new BoxLayout(wTab, BoxLayout.Y_AXIS));
        wG = new ButtonGroup();
        wClosed = new JRadioButton("fenÃ¨tre fermÃ©e");
        wMOpened = new JRadioButton("fenÃ¨tre vers ouverte");
        wOpened = new JRadioButton("fenÃ¨tre ouverte");

        wG.add(wClosed); wG.add(wMOpened); wG.add(wOpened);

        wTab.add(new JLabel("Choisissez un choix pour l'ouverture de fenetre"));
        wTab.add(Box.createRigidArea(new Dimension(5, 20)));
        wTab.add(wClosed);
        wTab.add(Box.createRigidArea(new Dimension(5, 20)));
        wTab.add(wMOpened);
        wTab.add(Box.createRigidArea(new Dimension(5, 20)));
        wTab.add(wOpened);
        tp.addTab("FenÃªtre", wTab);
        tp.addTab("Store", new JLabel("store tab here"));
        tp.addTab("Teinte", new JLabel("Teinte tab here"));
        tp.addTab("Temperature", new JLabel("Temperature tab here"));

        content.add(tp, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton confirmBtn = new JButton("Confirmer");
        JButton cancelBtn = new JButton("Annuler");

        footer.add(confirmBtn);
        footer.add(cancelBtn);

        content.add(footer, BorderLayout.PAGE_END);

        this.setContentPane(content);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(frame);
    }


}
