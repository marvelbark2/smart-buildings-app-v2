package edu.episen.si.ing1.pds.client.swing.ConfigFenetre;
import edu.episen.si.ing1.pds.client.swing.cards.access.AccessRequests;
import edu.episen.si.ing1.pds.client.swing.global.Main;
import edu.episen.si.ing1.pds.client.swing.global.Navigate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class EntryPoint implements Navigate  {

    private final Logger logger = LoggerFactory.getLogger(EntryPoint.class.getName());
    private final Main app;
    private JButton okBtn;
    private Map windowSelected;

    public EntryPoint(Main app) {
        this.app = app;
    }

    @Override
    public void start() {
        JPanel context = app.getContext();
        JPanel workspacePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        workspacePanel.setOpaque(true);

        List<Map> buildings = AccessRequests.getBuildingList();
        JComboBox buldingsCombobox = new JComboBox(new Vector(buildings));
        buldingsCombobox.setSize(250, buldingsCombobox.getPreferredSize().height);
        buldingsCombobox.setPreferredSize(buldingsCombobox.getSize());
        buldingsCombobox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof Map) {
                    Map building = (Map) value;
                    setText(building.get("name").toString());
                }
                if(index == -1 && value == null)
                    setText("Selectionner batiment");

                return this;
            }
        });
        buldingsCombobox.setSelectedIndex(-1);

        String[] floors = { "-" };
        JComboBox comboBox = new JComboBox(floors);
        comboBox.setEnabled(false);
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof Map) {
                    Map floor = (Map) value;
                    setText(floor.get("name").toString());
                }
                if(index == -1 && value == null)
                    setText("Selectionner etage");

                return this;
            }
        });

        String[] desks = { "-" };
        JComboBox comboBox2 = new JComboBox(desks);
        comboBox2.setEnabled(false);
        comboBox2.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof Map) {
                    Map desk = (Map) value;
                    setText(desk.get("name").toString());
                }
                if(index == -1 && value == null)
                    setText("Selectionner espace de travail");

                return this;
            }
        });

        okBtn = new JButton("OK");
        okBtn.setEnabled(false);
        okBtn.addActionListener(this);

        buldingsCombobox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if(buldingsCombobox.getSelectedIndex() != -1) {
                    comboBox.removeAllItems();
                    Map building = (Map) buldingsCombobox.getSelectedItem();
                    List<Map> floors = AccessRequests.getFloorList((Integer) building.get("building_id"));
                    floors.forEach(comboBox::addItem);
                    comboBox.setEnabled(true);
                    comboBox.setSize(250, comboBox.getPreferredSize().height);
                    comboBox.setPreferredSize(comboBox.getSize());
                    comboBox.setSelectedIndex(-1);
                    comboBox2.setEnabled(false);
                    okBtn.setEnabled(false);
                }
            }
        });
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if(comboBox.getSelectedIndex() != -1) {
                    comboBox2.removeAllItems();
                    Map floor = (Map) comboBox.getSelectedItem();
                    List<Map> workspaces = AccessRequests.getWorkspaceList((Integer) floor.get("floor_id"));
                    workspaces.forEach(comboBox2::addItem);
                    comboBox2.setEnabled(true);
                    comboBox2.setSize(250, comboBox2.getPreferredSize().height);
                    comboBox2.setPreferredSize(comboBox2.getSize());
                    comboBox2.setSelectedIndex(-1);
                }
            }
        });
        comboBox2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if(comboBox2.getSelectedIndex() != -1) {
                    Map<String, Map> data = new HashMap<>();
                    data.put("building", (Map) buldingsCombobox.getSelectedItem());
                    data.put("floor", (Map) comboBox.getSelectedItem());
                    data.put("space", (Map) comboBox2.getSelectedItem());

                    windowSelected = data;
                    okBtn.setEnabled(true);
                }
            }
        });

        workspacePanel.add(buldingsCombobox);
        workspacePanel.add(comboBox);
        workspacePanel.add(comboBox2);
        workspacePanel.add(okBtn);
        context.add(workspacePanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            if(e.getSource() instanceof JButton) {
                JButton btn = (JButton) e.getSource();
                if(btn.equals(okBtn)) {
                    logger.info("Switching to {}", windowSelected);
                    new WindowPicker(app.getFrame(), windowSelected);
                }
            }
    }
}


