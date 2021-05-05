package edu.episen.si.ing1.pds.client.swing.cards.card.listeners;

import edu.episen.si.ing1.pds.client.swing.cards.ComponentRegister;
import edu.episen.si.ing1.pds.client.swing.cards.ContextFrame;
import edu.episen.si.ing1.pds.client.swing.cards.card.CardRequests;
import edu.episen.si.ing1.pds.client.swing.cards.models.CardTableModel;
import edu.episen.si.ing1.pds.client.swing.cards.models.DataTable;
import edu.episen.si.ing1.pds.client.swing.cards.models.DualListBox;
import edu.episen.si.ing1.pds.client.swing.cards.user.UserRequests;
import edu.episen.si.ing1.pds.client.swing.global.shared.toast.Toast;
import edu.episen.si.ing1.pds.client.utils.Utils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class CardButtonListener implements ActionListener {
    ComponentRegister register = ComponentRegister.Instance;
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        if(source.equals(register.getButton("card_edit"))) {
            JTable table = register.getTable("card_table");
            ContextFrame context = (ContextFrame) register.getContainer("context");
            int selectedRow = table.getSelectedRow();
                if (table.isRowSelected(selectedRow)) {
                    DataTable cardDataTable = register.getDataTable("card_dataTable");
                    List<Map> cardList =  cardDataTable.getDataSource();
                    String cardIdRow = cardList.get(selectedRow).get(cardDataTable.getColumnName(0)).toString();
                    int cardId = Integer.parseInt(cardIdRow);
                    Map<String, Object> responseBody = CardRequests.fetchCardById(cardId);
                    Map<String, Object> data = (Map<String, Object>) responseBody.get("card");
                    JDialog dialog = new JDialog(context.frame());
                    dialog.setSize(700,800);

                    Toast toast = new Toast(context.getApp().getContext());

                    dialog.setPreferredSize(dialog.getSize());
                    JPanel dialogPanel = new JPanel(new GridLayout(4,2, 25, 25));
                    dialogPanel.setBorder(BorderFactory.createTitledBorder("Modifer la carte"));


                    List userList = UserRequests.all();
                    JComboBox comboBox = new JComboBox(new Vector(userList));
                    comboBox.setPreferredSize(new Dimension(250, 25));

                    comboBox.setRenderer(new DefaultListCellRenderer() {
                        @Override
                        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                            if (value instanceof Map) {
                                Map<String, String> val = (Map<String, String>) value;
                                setText(val.get("name"));
                            }
                            if(index == -1 && value == null) {
                                setText(((Map) data.get("user")).get("name").toString());
                            }
                            return this;
                        }
                    });

                    comboBox.addActionListener(evt -> {
                        JComboBox self = (JComboBox) evt.getSource();
                        Map o = (Map) self.getSelectedItem();
                    });

                    comboBox.setSelectedIndex(-1);

                    JPanel userFieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    JLabel userFieldFor = new JLabel("Utilisateur");
                    userFieldPanel.add(userFieldFor);
                    userFieldPanel.add(comboBox);

                    JPanel serialFieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    JLabel serialFieldFor = new JLabel("Matricule");
                    JTextField serialField = new JTextField(20);
                    serialField.setText(data.get("cardUId").toString());
                    JButton snGenerator = new JButton("Generer 1");
                    snGenerator.addActionListener(evt -> serialField.setText(Utils.generateSerialNumber()));
                    serialFieldPanel.add(serialFieldFor);
                    serialFieldPanel.add(serialField);
                    serialFieldPanel.add(snGenerator);

                    JPanel expirableFieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    JCheckBox expirableField = new JCheckBox();
                    expirableField.setSelected((Boolean) data.get("expirable"));
                    JLabel expirableFieldFor = new JLabel("Expirable");
                    expirableFieldPanel.add(expirableFieldFor);
                    expirableFieldPanel.add(expirableField);

                    JPanel expiredDate = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    JTextField expiredDateField = new JTextField(12);
                    expiredDateField.setText(data.get("expiredDate") == null ? "Infini" : data.get("expiredDate").toString() );
                    expiredDateField.setEnabled(expirableField.isSelected());
                    JLabel expiredDateFieldFor = new JLabel("Date d'expiration");
                    expiredDate.add(expiredDateFieldFor);
                    expiredDate.add(expiredDateField);

                    expirableField.addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {
                            expiredDateField.setEnabled(expirableField.isSelected());
                        }
                    });

                    JPanel textFields = new JPanel(new GridLayout(2,2, 25,25));
                    textFields.add(userFieldPanel);
                    textFields.add(serialFieldPanel);
                    textFields.add(expirableFieldPanel);
                    textFields.add(expiredDate);

                    dialogPanel.add(textFields);

                    JPanel active = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    JCheckBox activeCard = new JCheckBox("Carte Active");
                    activeCard.setSelected((Boolean) data.get("active"));
                    active.add(activeCard);

                    dialogPanel.add(active);

                    DualListBox dual = new DualListBox("accessible");

                    List<Map> dataAccessList = CardRequests.getCardAccessList(Map.of("serialId", data.get("cardUId")));
                    dataAccessList.forEach(map ->map.put("edited", false));

                    List<Map> accessible = dataAccessList.stream().filter(map -> (boolean)map.get("accessible")).collect(Collectors.toList());
                    List<Map> notAccessible = dataAccessList.stream().filter(map -> ! (boolean)map.get("accessible")).collect(Collectors.toList());



                    dual.addSourceElements(notAccessible.toArray(new Map[0]));
                    dual.addDestinationElements(accessible.toArray(new Map[0]));
                    dual.setRenderer(new DefaultListCellRenderer() {
                        @Override
                        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                            if (value instanceof Map) {
                                Map<String, String> val = (Map<String, String>) value;
                                setText(val.get("title"));
                            }
                            return this;
                        }
                    });
                    dual.setBorder(BorderFactory.createTitledBorder("Gérer les accès"));
                    dialogPanel.add(dual);

                    JButton submit = new JButton("Soumttre");
                    submit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Map<String, Object> card = new HashMap<>();
                            card.put("cardUId", serialField.getText());
                            card.put("expirable", expirableField.isSelected());
                            if(expiredDateField.getText().equalsIgnoreCase("infini"))
                                card.put("expiredDate", null);
                            else
                                card.put("expiredDate", expiredDateField.getText());
                            card.put("active", activeCard.isSelected());
                            Object user = comboBox.getSelectedItem();
                            if(user == null)
                                card.put("user", data.get("user"));
                            else
                                card.put("user",user);

                            Iterator source = dual.sourceIterator();
                            Iterator dest = dual.destinationIterator();

                            List not = new ArrayList();
                            List accessible = new ArrayList();

                            while (source.hasNext())
                                not.add(source.next());

                            while (dest.hasNext())
                                accessible.add(dest.next());

                            List items = new ArrayList();
                            items.addAll(not);
                            items.addAll(accessible);

                            Map editData = Map.of(
                                    "card", card,
                                    "card_id", data.get("cardId"),
                                    "workspaces", items
                            );
                            Boolean editRes = CardRequests.updateCard(editData);
                            if(editRes) {
                                toast.success("La modification est bien faite");
                                DataTable cardTableModel = new CardTableModel();
                                register.registerDataTable("card_table", cardTableModel);
                                table.setModel(cardTableModel);
                                dual.clearDestinationListModel();
                                dual.clearSourceListModel();
                                dialog.dispose();
                            } else {
                                toast.error("Erreur! Il y a un problème");
                            }
                        }
                    });

                    dialogPanel.add(submit);

                    dialog.setContentPane(dialogPanel);
                    dialog.setVisible(true);
                    dialog.pack();
                }
                else {
                   // toast.warn("Selectionnez une ligne dans le tableau");
                    System.out.println("Erreur! Il y a un problème");
                }
        }
    }
}
