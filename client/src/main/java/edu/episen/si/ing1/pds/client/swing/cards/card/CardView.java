package edu.episen.si.ing1.pds.client.swing.cards.card;

import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.swing.cards.ComponentRegister;
import edu.episen.si.ing1.pds.client.swing.cards.ContextFrame;
import edu.episen.si.ing1.pds.client.swing.cards.Routes;
import edu.episen.si.ing1.pds.client.swing.cards.card.listeners.CardButtonListener;
import edu.episen.si.ing1.pds.client.swing.cards.models.CardTableModel;
import edu.episen.si.ing1.pds.client.swing.cards.models.DualListBox;
import edu.episen.si.ing1.pds.client.swing.global.MenuItem;
import edu.episen.si.ing1.pds.client.swing.global.shared.Ui;
import edu.episen.si.ing1.pds.client.swing.global.shared.toast.Toast;
import edu.episen.si.ing1.pds.client.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class CardView implements Routes {
    private final Logger logger = LoggerFactory.getLogger(CardView.class.getName());
    private Toast toast;
    private List<Map> dataTable;

    @Override
    public void launch(ContextFrame context) {
        JPanel frame = context.getApp().getContext();

        ComponentRegister register = ComponentRegister.Instance;
        register.registerContainer("context", context);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        toast = new Toast(panel);

        CardTableModel tableModel = new CardTableModel();
        dataTable = tableModel.getDataSource();
        JTable table = new JTable(tableModel);


        register.registerTable("card_table", table);
        register.registerDataTable("card_dataTable", tableModel);

        handlecardStatus(table, tableModel);

        table.setRowSelectionAllowed(true);

        JTableHeader header = table.getTableHeader();

        JTableHeader th = table.getTableHeader();
        TableColumnModel tcm = th.getColumnModel();

        TableColumn tc = tcm.getColumn(0);
        tc.setHeaderValue( "ID" );

        TableColumn tc2 = tcm.getColumn(1);
        tc2.setHeaderValue( "Matricule" );

        TableColumn tc3 = tcm.getColumn(3);
        tc3.setHeaderValue( "Date d'expiration" );

        TableColumn tc4 = tcm.getColumn(4);
        tc4.setHeaderValue( "Utilisateur" );

        th.repaint();
        header.setBackground(Ui.COLOR_INTERACTIVE_DARKER);
        header.setForeground(Ui.OFFWHITE);
        JScrollPane sp = new JScrollPane(table);

        ListSelectionModel select = table.getSelectionModel();
        JPanel btnPanel = new JPanel(new GridLayout(1, 3, 20, 30));
        btnPanel.setBorder(BorderFactory.createTitledBorder("Operations"));

        ActionListener cardButtonListeners = new CardButtonListener();

        JButton edit = new JButton("Modifier");
        edit.setPreferredSize(new Dimension(40, 40));
        register.registerButton("card_edit", edit);

        JButton delete = new JButton("Supprimer");
        delete.setPreferredSize(new Dimension(40, 40));
        register.registerButton("card_delete", delete);

        JButton details = new JButton("details");
        details.setPreferredSize(new Dimension(40, 40));
        register.registerButton("card_details", details);

        details.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (table.isRowSelected(selectedRow)) {
                    Request request = new Request();
                    request.setEvent("card_byid");
                    request.setData(
                            Map.of(
                                    "id",
                                    Integer.parseInt(dataTable.get(selectedRow).get(tableModel.getColumnName(0)).toString())
                            )
                    );
                    Response response = Utils.sendRequest(request);
                    Map<String, Object> responseBody = (Map<String, Object>) response.getMessage();
                    Map<String, Object> data = (Map<String, Object>) responseBody.get("card");
                    Map<String, List<Map<String, List>>> accessList = (Map<String, List<Map<String, List>>>) responseBody.get("access_list");

                    JDialog dialog = new JDialog(context.frame());
                    dialog.setSize(1000, 1000);
                    dialog.setPreferredSize(dialog.getSize());

                    UIManager.put("TabbedPane.selected", new Color(72, 64, 92));

                    JPanel contentPane = new JPanel(new BorderLayout(20, 20));

                    JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));

                    MenuItem title = new MenuItem("Voir détails d'une carte");
                    header.add(title);
                    contentPane.add(header, BorderLayout.PAGE_START);

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
                    for (String building: accessList.keySet()) {
                        Map buildingMap = Utils.toMap(building);
                        DefaultMutableTreeNode buildNode = new DefaultMutableTreeNode(buildingMap.get("name"));
                        for (Object floor: accessList.get(building)) {
                            Map<Map, List> floorN = (Map) floor;
                            for (Object works: floorN.keySet()) {
                                Map floorMap = Utils.toMap(works.toString());
                                DefaultMutableTreeNode floorNode = new DefaultMutableTreeNode(floorMap.get("floor"));
                                buildNode.add(floorNode);
                                for (List<Map> dataN: floorN.values()) {
                                    for (Map workspaces: dataN) {
                                        DefaultMutableTreeNode workNode = new DefaultMutableTreeNode(new HashMap<>(workspaces));
                                        floorNode.add(workNode);
                                    }
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
                                boolean bool = (Boolean)val.get("access");
                                String path = "";
                                if(bool) {
                                    path = "icon/granted.png";
                                    logger.info(val.toString());
                                } else {
                                    path = "icon/forbidden.png";
                                }
                                setText(val.get("workspace_type").toString() + " - " + bool);

                                Image icon = Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource(path));
                                Image resised = icon.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
                                setIcon(new ImageIcon(resised));
                            }
                            return this;
                        }
                    });
                    tree.setRootVisible(false);
                    infoPanel.add(tree);

                    JPanel history = new JPanel(new GridLayout(1,1));

                    JTable histoTable = new JTable(rows, new String[]{ " ", " " });
                    histoTable.setOpaque(false);
                    history.add(histoTable);

                    dialogPanel.add("Info sur Carte", infoPanel);
                    dialogPanel.add("Historique de carte", histoTable);

                    contentPane.add(dialogPanel, BorderLayout.CENTER);

                    dialog.setContentPane(contentPane);
                    dialog.setVisible(true);
                    dialog.pack();
                } else {
                    toast.warn("Selectionnez une ligne dans tableau");
                }
            }
        });
        delete.addActionListener((e) -> {
            int selectedRow = table.getSelectedRow();
            if (table.isRowSelected(selectedRow)) {
                int index = dataTable.size() > selectedRow ? selectedRow : selectedRow - 1;
                Map<String, Object> hm = dataTable.get(index);
                hm.remove("cardId");
                Request request = new Request();
                request.setEvent("card_delete");
                request.setData(hm);
                Response response = Utils.sendRequest(request);
                logger.info(response.getMessage().toString());
                if (response.getEvent().equals("card_delete") && response.getMessage().equals(true)) {
                    toast.success("La suppression est bien faite");
                    updateTable(table, tableModel);
                } else {
                    toast.info("Erreur ! Il existe un probleme !");
                }
            } else {
                toast.warn("Selectionner une ligne pour lui supprimer");
                logger.error("Select a row");
            }
        });
        edit.addActionListener(cardButtonListeners);

        btnPanel.add(edit, BorderLayout.CENTER);
        btnPanel.add(delete, BorderLayout.CENTER);
        btnPanel.add(details);

        select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        Border blackline = BorderFactory.createTitledBorder("Inserer une carte");
        JPanel formPanel = new JPanel();
        LayoutManager layout = new GridLayout(2, 2);
        formPanel.setLayout(layout);
        formPanel.setBorder(blackline);

        Request requestUserList = new Request();
        requestUserList.setEvent("user_list");
        Response response = Utils.sendRequest(requestUserList);
        List userList = (List) response.getMessage();
        JComboBox comboBox = new JComboBox(new Vector(userList));

        register.registerComboBox("user_list", comboBox);
        comboBox.setPreferredSize(new Dimension(250, 25));
        comboBox.setSelectedIndex(-1);
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Map)
                    setText(((Map<String, String>) value).get("name"));
                if (index == -1 && value == null)
                    setText("Selectionner un utilisateur");

                return this;
            }
        });

        comboBox.addActionListener(e -> {
            JComboBox self = (JComboBox) e.getSource();
            Map o = (Map) self.getSelectedItem();
        });

        //ComboBox field
        JPanel comboField = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        comboField.setOpaque(false);
        JLabel comboFieldFor = new JLabel("User: ");
        Border comboBorder = BorderFactory.createTitledBorder("Champ Utilisateur");
        comboField.add(comboFieldFor);
        comboField.add(comboBox);
        comboField.setBorder(comboBorder);
        formPanel.add(comboField);

        //Serial Number flied
        JPanel sNField = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        sNField.setOpaque(false);
        JLabel sNFieldFor = new JLabel("Matricule: ");
        JTextField snFieldText = new JTextField(20);
        JButton snGenerator = new JButton("Generer 1");
        snGenerator.addActionListener(e -> snFieldText.setText(Utils.generateSerialNumber()));
        Border sNBorder = BorderFactory.createTitledBorder("Champ Matricule");
        sNField.add(sNFieldFor);
        sNField.add(snFieldText);
        sNField.add(snGenerator);
        sNField.setBorder(sNBorder);
        formPanel.add(sNField);

        //tmp card field
        JPanel expirableField = new JPanel(new FlowLayout(FlowLayout.CENTER));
        expirableField.setOpaque(false);
        Border expirableBorder = BorderFactory.createTitledBorder("Expirable");
        expirableField.setBorder(expirableBorder);
        JCheckBox expirable = new JCheckBox("Carte Provisoire ?");
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        JFormattedTextField expirableDate = new JFormattedTextField(format);
        expirableDate.setColumns(12);
        expirableDate.setEnabled(false);
        try {
            expirableDate.commitEdit();
        } catch (ParseException e) {

        }
        expirable.addChangeListener((e) -> expirableDate.setEnabled(expirable.isSelected()));
        expirableField.add(expirable);
        formPanel.add(expirableField);

        // Expire Date Field
        JPanel expireDate = new JPanel(new FlowLayout(FlowLayout.CENTER));
        expireDate.setOpaque(false);
        Border expireDateBorder = BorderFactory.createTitledBorder("Date d'expiration");
        expireDate.setBorder(expireDateBorder);
        JLabel expireDateLabel = new JLabel("Date: ");
        expireDate.add(expireDateLabel);
        expireDate.add(expirableDate);
        formPanel.add(expireDate);

        //Submit Button
        JPanel submit = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton submitButton = new JButton("Soumettre");
        submitButton.setSize(100, 100);
        submitButton.addActionListener((e) -> {
            if ((snFieldText.getText() != null && snFieldText.getText().length() >= 20) && (comboBox.getSelectedItem() != null || comboBox.getSelectedIndex() != -1)) {
                String expireDateValue = "";
                if (expirable.isSelected())
                    expireDateValue = expirableDate.getText();

                Map insertCardReq =  Map.of("cardUId", snFieldText.getText(), "expirable", expirable.isSelected(), "expiredDate", expireDateValue, "user", comboBox.getSelectedItem());
                Boolean inserted = tableModel.addData(insertCardReq);
                if(inserted)
                    updateTable(table, tableModel);
                else
                    toast.error("Erreur! Duplication cartes pour 1 utilisateur");

            } else {
                toast.error("Erreur ! Veuillez remplir la formulaire");
            }
        });
        submit.add(submitButton);

        JButton returnBack = new JButton("Retourner");
        returnBack.addActionListener(e -> {
            context.returnHere();
            panel.setVisible(false);
        });
        Box buttonsPanel = Box.createVerticalBox();
        buttonsPanel.add(returnBack);
        returnBack.setBackground(new Color(2, 117, 216));
        returnBack.setOpaque(true);

        buttonsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);//0.0

        panel.add(sp);
        panel.add(btnPanel);
        panel.add(formPanel);
        panel.add(submit);

        panel.setOpaque(false);
        sp.setOpaque(false);
        formPanel.setOpaque(false);
        btnPanel.setOpaque(false);
        submit.setOpaque(false);


        frame.add(panel);
    }
    private void updateTable(JTable table, CardTableModel modelTable) {
        modelTable = new CardTableModel();
        dataTable = modelTable.getDataSource();
        table.setModel(modelTable);
        table.repaint();
    }
    private void handlecardStatus(JTable table, CardTableModel modelTable) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if(modelTable.getDataSource().size() > row) {
                    final Boolean bool = (Boolean) modelTable.getDataSource().get(row).get("active");
                    Object expired = modelTable.getDataSource().get(row).get("expiredDate");
                    if(expired != null) {
                        try {
                            String expire = expired.toString();
                            Date today = new Date();
                            Date expiredDate = new SimpleDateFormat("dd/MM/yyyy").parse(expire);
                            if(today.after(expiredDate)){
                                Font font = Ui.FONT_GENERAL_UI;
                                Map  attributes = font.getAttributes();
                                attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
                                setFont(new Font(attributes));
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if(!bool) {
                        setBackground(Color.RED);
                    }
                    setOpaque(isSelected);
                }
                return this;
            }
        });
    }
}
