package edu.episen.si.ing1.pds.client.swing.cards.card.dialogs;

import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.swing.cards.ContextFrame;
import edu.episen.si.ing1.pds.client.swing.cards.card.CardRequests;
import edu.episen.si.ing1.pds.client.swing.global.MenuItem;
import edu.episen.si.ing1.pds.client.swing.global.shared.Ui;
import edu.episen.si.ing1.pds.client.utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class CardReadDialog implements Dialogs {
    private JDialog dialog;

    public CardReadDialog(ContextFrame context, Map<String, Object> data) {
        List<Map> treeview = CardRequests.cardTree(data);


        dialog = new JDialog(context.frame());
        dialog.setSize(1000, 1000);
        dialog.setPreferredSize(dialog.getSize());

        UIManager.put("TabbedPane.selected", new Color(72, 64, 92));

        JPanel contentPane = new JPanel(new BorderLayout(20, 20));

        edu.episen.si.ing1.pds.client.swing.global.MenuItem title = new MenuItem("Voir détails d'une carte");
        contentPane.add(title, BorderLayout.PAGE_START);

        JTabbedPane dialogPanel = new JTabbedPane();
        dialogPanel.setOpaque(true);
        dialogPanel.setForeground(Ui.OFFWHITE);
        dialogPanel.setBackground(Ui.COLOR_INTERACTIVE);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 40,40));

        String[][] rows = {
                { "Matricule", data.get("cardUId").toString() },
                { "Provisoire", data.get("expirable").equals(true) ? "Oui" : "Non" },
                { "Nom d'utilisateur", ( (Map) data.get("user")).get("name").toString() },
                { "Expiration", data.get("expiredDate") == null ? "Infini" : data.get("expiredDate").toString()},
                { "Utisilateur", ((Map)data.get("user")).get("name").toString() }
        };
        JTable showTable = new JTable(rows, new String[]{ " ", " " });
        showTable.setOpaque(false);
        showTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                return this;
            }
        });
        infoPanel.add(showTable);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("r");

        for (Map building: treeview) {
            DefaultMutableTreeNode buildNode = new DefaultMutableTreeNode(building);
            List<Map> floors = (List<Map>) building.get("floors");
            for (Map floor: floors) {
                DefaultMutableTreeNode floorNode = new DefaultMutableTreeNode(floor);
                buildNode.add(floorNode);
                List<Map> wokspaces = (List<Map>) floor.get("workspaces");
                if(wokspaces != null) {
                    for(Map wokspace: wokspaces) {
                        DefaultMutableTreeNode workspace = new DefaultMutableTreeNode(wokspace);
                        List<Map> equipments = (List<Map>) wokspace.get("equipments");
                        if(equipments != null) {
                            for (Map equipment: equipments) {
                                DefaultMutableTreeNode equipNode = new DefaultMutableTreeNode(equipment);
                                workspace.add(equipNode);
                            }
                        }
                        floorNode.add(workspace);
                    }
                }
            }
            root.add(buildNode);
        }
        JTree tree = new JTree(root);
        tree.setCellRenderer(new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                Object object = ((DefaultMutableTreeNode) value).getUserObject();
                if(object instanceof Map) {
                    Map val = (Map) object;
                    if(val.containsKey("accessible")) {
                        boolean bool = (Boolean)val.get("accessible");
                        String path = "";
                        if(bool) {
                            path = "icon/granted.png";
                        } else {
                            path = "icon/forbidden.png";
                        }
                        Image icon = Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource(path));
                        Image resised = icon.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
                        setIcon(new ImageIcon(resised));
                    }
                    setText(val.get("name").toString());
                }
                return this;
            }
        });
        tree.setRootVisible(false);
        JScrollPane span = new JScrollPane(tree);
        infoPanel.add(span);

        JPanel history = new JPanel(new GridLayout(1,1));

        JTable histoTable = new JTable(rows, new String[]{ " ", " " });
        histoTable.setOpaque(false);
        history.add(histoTable);

        dialogPanel.add("Info sur Carte", infoPanel);
        dialogPanel.add("Historique de carte", histoTable);

        contentPane.add(dialogPanel, BorderLayout.CENTER);

        dialog.setContentPane(contentPane);
        dialog.setLocationRelativeTo(context.frame());
        dialog.setVisible(true);
    }

    @Override
    public JDialog getDialog() {
        return dialog;
    }
}
