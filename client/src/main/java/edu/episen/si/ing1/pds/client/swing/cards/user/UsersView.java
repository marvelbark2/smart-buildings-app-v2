package edu.episen.si.ing1.pds.client.swing.cards.user;

import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.swing.cards.ContextFrame;
import edu.episen.si.ing1.pds.client.swing.cards.Routes;
import edu.episen.si.ing1.pds.client.swing.cards.models.DataTable;
import edu.episen.si.ing1.pds.client.swing.cards.models.UserTableModel;
import edu.episen.si.ing1.pds.client.swing.cards.roles.RoleRequests;
import edu.episen.si.ing1.pds.client.swing.global.shared.toast.Toast;
import edu.episen.si.ing1.pds.client.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class UsersView implements Routes {
    private JTable table;
    private final Logger logger = LoggerFactory.getLogger(UsersView.class.getName());
    private Toast toastr;

    @Override
    public void launch(ContextFrame context) {
        JPanel frame = context.getApp().getContext();

        toastr = new Toast(frame);
        frame.setLayout(new GridLayout(3,1, 100, 100));

        DataTable model = new UserTableModel();
        table = new JTable(model);

        table.setVisible(true);

        TableRowSorter<DataTable> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JPanel tablePanel = new JPanel(new BorderLayout());

        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setPreferredSize( new Dimension(400, 600) );

        JPanel searchBar = new JPanel(new BorderLayout());
        JTextField searchText = new JTextField(20);
        searchText.setBorder(BorderFactory.createTitledBorder("Rechercher"));
        searchText.getDocument().addDocumentListener(new DocumentListener() {
            private void searchByTewt(DocumentEvent event) {
                String text = searchText.getText();
                if (text.length() == 0) {
                    sorter.setRowFilter(null);
                    table.clearSelection();
                } else {
                    try {
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                        table.clearSelection();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }

            @Override
            public void insertUpdate(DocumentEvent event) {
                searchByTewt(event);
            }

            @Override
            public void removeUpdate(DocumentEvent event) {
                searchByTewt(event);
            }

            @Override
            public void changedUpdate(DocumentEvent event) {
                searchByTewt(event);
            }
        });
        searchBar.add(searchText, BorderLayout.EAST);
        tablePanel.add(searchBar, BorderLayout.PAGE_START);
        tablePanel.add(jScrollPane, BorderLayout.CENTER);

        JButton button = new JButton("Refresh Table");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });

        Map selectedUser = new HashMap();

        JButton edit = new JButton("Modifier");
        JButton delete = new JButton("Supprimer");
        JButton read = new JButton("details");


        read.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int selectedRow = table.getSelectedRow();
                if(table.isRowSelected(selectedRow)) {
                    Map user = model.getDataSource().get(selectedRow);
                    new UserDetails(context, user);
                } else {
                    toastr.warn("Selectionner un utilisateur");
                }
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int selectedRow = table.getSelectedRow();
                if(table.isRowSelected(selectedRow)) {
                    Map user = model.getDataSource().get(selectedRow);
                    Boolean deleteUser = UserRequests.deleteUser(user);
                    if(deleteUser) {
                        toastr.success("Utilisateur est bien supprimer");
                        refresh();
                    } else
                        toastr.error("Il y a un erreur");
                } else
                    toastr.warn("Selectionnez un utilisateur");
            }
        });

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actionPanel.add(edit, FlowLayout.LEFT);
        actionPanel.add(delete, FlowLayout.CENTER);
        actionPanel.add(read, FlowLayout.RIGHT);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 50,50));

        JPanel nameFieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextField nameField = new JTextField(10);
        nameFieldPanel.add(new JLabel("Nom"));
        nameFieldPanel.add(nameField);

        JPanel roleFieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        List<Map> roleList = RoleRequests.fetchRoleList();
        JComboBox comboBox = new JComboBox(new Vector(roleList));
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof Map) {
                    Map role = (Map) value;
                    setText(role.get("abbreviation") + " - " + role.get("designation"));
                }
                return this;
            }
        });
        roleFieldPanel.add(new JLabel("Role"));
        roleFieldPanel.add(comboBox);

        formPanel.add(roleFieldPanel);
        formPanel.add(nameFieldPanel);

        JPanel buttonPanel = new JPanel(new BorderLayout());

        buttonPanel.add(button, BorderLayout.PAGE_START);
        buttonPanel.add(actionPanel, BorderLayout.CENTER);

        JButton insert = new JButton("Soumettre");
        insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nameField.getText().isEmpty()) {
                    toastr.error("Remplisser formulaire");
                } else {
                    if(selectedUser.size() == 0) {
                        Map data = Map.of(
                                "name", nameField.getText(),
                                "role", Objects.requireNonNull(comboBox.getSelectedItem())
                        );
                        logger.info(comboBox.getSelectedItem().toString());
                        Boolean response = model.addData(data);
                        if(response) {
                            toastr.success("Utilisateur est bien enregister");
                            refresh();
                        }
                    } else {
                        Map data = Map.of(
                                "name", nameField.getText(),
                                "userUId", selectedUser.get("userUId"),
                                "role", Objects.requireNonNull(comboBox.getSelectedItem())
                        );
                        Boolean response = UserRequests.updateUser(data);
                        if(response) {
                            toastr.success("Updated user");
                            refresh();
                        } else
                            toastr.error("Il y a un erreur !");
                        selectedUser.clear();
                        comboBox.setSelectedIndex(0);
                        nameField.setText("");
                    }
                }
            }
        });
        insert.setBounds(30,30, 50, 50);

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int selectedRow = table.getSelectedRow();
                if(table.isRowSelected(selectedRow)) {
                    selectedUser.putAll(model.getDataSource().get(selectedRow));
                    nameField.setText(selectedUser.get("name").toString());
                    Map selectedRole = (Map) selectedUser.get("role");
                    logger.info(comboBox.getItemAt(0).toString());
                    for (int i = 0; i < comboBox.getItemCount(); i++) {
                        Map itemIn = (Map) comboBox.getItemAt(i);
                        if(selectedRole.get("roleId").equals(itemIn.get("roleId"))){
                            comboBox.setSelectedIndex(i);
                            logger.info("found");
                        }
                    }
                    comboBox.setSelectedItem(selectedUser.get("role"));
                }
            }
        });

        frame.add(tablePanel, JPanel.CENTER_ALIGNMENT);
        frame.add(buttonPanel);
        frame.add(formPanel);
        frame.add(insert);

        frame.setVisible(true);
    }
    private void refresh() {
        table.setModel(new UserTableModel());
        table.invalidate();
        table.validate();
        table.repaint();
        toastr.success("Tableau est bien rafraîchi");
    }
}
