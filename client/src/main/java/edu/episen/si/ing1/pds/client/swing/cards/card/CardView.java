package edu.episen.si.ing1.pds.client.swing.cards.card;

import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.swing.cards.ComponentRegister;
import edu.episen.si.ing1.pds.client.swing.cards.ContextFrame;
import edu.episen.si.ing1.pds.client.swing.cards.Routes;
import edu.episen.si.ing1.pds.client.swing.cards.card.dialogs.CardReadDialog;
import edu.episen.si.ing1.pds.client.swing.cards.card.dialogs.Dialogs;
import edu.episen.si.ing1.pds.client.swing.cards.card.listeners.CardButtonListener;
import edu.episen.si.ing1.pds.client.swing.cards.models.CardTableModel;
import edu.episen.si.ing1.pds.client.swing.cards.user.UserRequests;
import edu.episen.si.ing1.pds.client.swing.global.shared.Ui;
import edu.episen.si.ing1.pds.client.swing.global.shared.toast.Toast;
import edu.episen.si.ing1.pds.client.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.*;
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
                    Map<String, Object> responseBody = CardRequests.fetchCardById(Integer.parseInt(dataTable.get(selectedRow).get(tableModel.getColumnName(0)).toString()));
                    Map<String, Object> data = (Map<String, Object>) responseBody.get("card");

                    Dialogs cardDialog = new CardReadDialog(context, data);
                    cardDialog.getDialog();

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
                Boolean deleted = CardRequests.deleteCard(hm);
                if (deleted) {
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

        List userList = UserRequests.all();
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
                if(inserted) {
                    snFieldText.setText("");
                    expirable.setSelected(false);
                    expirableDate.setText("");
                    comboBox.setSelectedIndex(-1);
                    updateTable(table, tableModel);
                    toast.success("Utilisateur est bien insere");
                }

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
