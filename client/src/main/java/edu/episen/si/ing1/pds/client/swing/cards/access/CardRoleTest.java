package edu.episen.si.ing1.pds.client.swing.cards.access;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.network.SocketFacade;
import edu.episen.si.ing1.pds.client.swing.cards.ContextFrame;
import edu.episen.si.ing1.pds.client.swing.cards.Routes;
import edu.episen.si.ing1.pds.client.swing.cards.card.CardRequests;
import edu.episen.si.ing1.pds.client.swing.global.shared.Ui;
import edu.episen.si.ing1.pds.client.swing.global.shared.toast.Toast;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class CardRoleTest implements Routes {

    @Override
    public void launch(ContextFrame context) {
        JPanel frame = context.getApp().getContext();

        JPanel panel = new JPanel(new GridLayout(3, 1, 100, 100));

        Toast toastr = new Toast(panel);

        JPanel cardPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cardPanel.setOpaque(true);

        List<Map> users = CardRequests.fetchCarcardList();
        JComboBox userList = new JComboBox(new Vector(users));

        userList.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
               if(value instanceof Map) {
                   Map node = (Map) value;
                   Map user = (Map) node.get("user");
                   setText(user.get("name").toString());
               }
               if(index == -1 && value == null)
                   setText("Selectionnez un utilisqteur");
                return this;
            }
        });
        userList.setSelectedIndex(-1);
        userList.setSize(250, userList.getPreferredSize().height);
        userList.setPreferredSize(userList.getSize());
        cardPanel.add(userList);

        cardPanel.add(new JLabel("CARD"));

        JTextField serialField = new JTextField(25);
        serialField.setEnabled(false);
        userList.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if(userList.getSelectedIndex() != -1) {
                    Map user = (Map) itemEvent.getItem();
                    serialField.setText(user.get("cardUId").toString());
                }
            }
        });
        serialField.setDisabledTextColor(Color.BLACK);
        serialField.setFont(Ui.FONT_COPYRIGHT);
        serialField.setOpaque(false);
        cardPanel.add(serialField);

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

        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton jButton = new JButton("Verifier");
        jButton.setEnabled(false);
        submitPanel.setOpaque(true);
        submitPanel.add(jButton);

        String[] equipment = { "-" };
        JComboBox equip = new JComboBox(equipment);
        equip.setEnabled(false);
        equip.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof Map) {
                    Map equip = (Map) value;
                    setText(equip.get("name").toString());
                }
                if(index == -1 && value == null)
                    setText("Selectionner equipement");

                return this;
            }
        });

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
                    jButton.setEnabled(false);
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
                    equip.setEnabled(false);
                }
            }
        });
        comboBox2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if(comboBox2.getSelectedIndex() != -1) {
                    equip.removeAllItems();
                    Map workspace = (Map) comboBox2.getSelectedItem();
                    List<Map> equipments = AccessRequests.getEquipmentList((Integer) workspace.get("workspace_id"));
                    equipments.forEach(equip::addItem);
                    equip.setEnabled(true);
                    equip.setSize(250, equip.getPreferredSize().height);
                    equip.setPreferredSize(equip.getSize());
                    equip.setSelectedIndex(-1);
                    jButton.setEnabled(true);
                }
            }
        });

        JPanel responsePanel = new JPanel();
        CardLayout cardLayout = new CardLayout(30, 30);
        responsePanel.setLayout(cardLayout);

        JPanel info = new JPanel(new FlowLayout(FlowLayout.CENTER));
        info.add(new JLabel("Veuillez rempli la formulaire", SwingConstants.CENTER));
        responsePanel.add(info, "info");

        JPanel rejected = new JPanel(new BorderLayout());
        JLabel rejectLabel = new JLabel("Cet utilisateur n'a pas l'accès", SwingConstants.CENTER);
        rejectLabel.setForeground(Color.WHITE);
        rejected.setBackground(new Color(90, 38, 54));
        rejected.add(rejectLabel, BorderLayout.CENTER);
        responsePanel.add(rejected, "rejected");

        JPanel granted = new JPanel(new BorderLayout());
        JLabel grantedLabel = new JLabel("Cet utilisateur peut acceder", SwingConstants.CENTER);
        granted.setBackground(new Color(3, 146, 26));
        grantedLabel.setForeground(Color.white);
        granted.add(grantedLabel, BorderLayout.CENTER);
        responsePanel.add(granted, "granted");

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Map workspace = (Map) comboBox2.getSelectedItem();
                Map equipment = (Map) equip.getSelectedItem();
                Map card = (Map) userList.getSelectedItem();
                if(card == null) {
                    toastr.error("Veuillez de choisir un utilisateur");
                } else {
                    boolean canActivate = false;
                    if (equipment == null) {
                        int workspaceId = (int) workspace.get("workspace_id");
                        Boolean access = AccessRequests.isAccessibleToWS(workspaceId, card);
                        canActivate = access;
                    } else {
                        int workspaceId = (int) equipment.get("equipment_id");
                        Boolean access = AccessRequests.isAccessibleToEquip(workspaceId, card);
                        canActivate = access;
                    }
                    if(canActivate) {
                        cardLayout.show(responsePanel, "granted");
                    } else {
                        cardLayout.show(responsePanel, "rejected");
                    }
                }

            }
        });


        workspacePanel.add(buldingsCombobox);
        workspacePanel.add(comboBox);
        workspacePanel.add(comboBox2);
        workspacePanel.add(new JLabel("/"));
        workspacePanel.add(equip);

        workspacePanel.add(submitPanel);

        panel.add(cardPanel);
        panel.add(workspacePanel);
        panel.add(responsePanel);

        frame.add(panel);
        frame.setVisible(true);
    }
}
