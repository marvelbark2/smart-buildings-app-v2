package edu.episen.si.ing1.pds.client.swing.ConfigFenetre.dialogs;

import edu.episen.si.ing1.pds.client.swing.ConfigFenetre.tools.WindowRequests;
import edu.episen.si.ing1.pds.client.swing.global.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.List;

public class WindowPicker extends JDialog implements ActionListener {
    private final Logger logger = LoggerFactory.getLogger(WindowPicker.class.getName());
    private JFrame frame;
    public WindowPicker(JFrame frame, Map<String, Map> spaceInfo){
        super(frame);
        this.frame = frame;
        this.setSize(1000, 1000);
        this.setPreferredSize(this.getSize());

        JPanel content = new JPanel(new BorderLayout(40, 40));
        Map building = spaceInfo.get("building");
        Map floor = spaceInfo.get("floor");
        Map space = spaceInfo.get("space");

        setTitle(""+space.get("name"));

        String headerText = "Nom de batiment: " + building.get("name") + " / Nom d'étage: " + floor.get("name") + " - Nom de salle:" + space.get("name");
        MenuItem header = new MenuItem(headerText);
        content.add(header, BorderLayout.PAGE_START);

        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel bluePrint = new JPanel(grid);
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        List<Map> windowsPosition = WindowRequests.getWindowsByWS((Integer) space.get("workspace_id"));
        for (Map position: windowsPosition) {
            int h = (int) position.get("height");
            int w = (int) position.get("width");

            gbc.gridx = (int) position.get("x");
            gbc.gridy = (int) position.get("y");
            gbc.gridwidth = w;
            gbc.gridheight = h;
            JButton btn = new JButton(""+position.get("position_id"));
            btn.addActionListener(this);
            btn.setActionCommand(""+position.get("position_id"));
            btn.setEnabled((Boolean) position.get("is_window"));
            bluePrint.add(btn, gbc);
        }

        content.add(bluePrint, BorderLayout.CENTER);

        this.setContentPane(content);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(frame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton){
            JButton btn = (JButton) e.getSource();
            String positionID = btn.getActionCommand();
            int id = Integer.parseInt(positionID);
            logger.info("position id {}", id);
            this.dispose();
            new WindowConfig(frame, id);
        }
    }
}
