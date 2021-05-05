package edu.episen.si.ing1.pds.client.swing.cards.roles;

import edu.episen.si.ing1.pds.client.swing.cards.ContextFrame;
import edu.episen.si.ing1.pds.client.swing.cards.Routes;
import edu.episen.si.ing1.pds.client.swing.cards.models.DataTable;
import edu.episen.si.ing1.pds.client.swing.cards.models.DualListBox;
import edu.episen.si.ing1.pds.client.swing.cards.models.RoleTableModel;
import edu.episen.si.ing1.pds.client.swing.cards.user.UserRequests;
import edu.episen.si.ing1.pds.client.swing.global.MenuItem;
import edu.episen.si.ing1.pds.client.swing.global.shared.toast.Toast;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.awt.*;
import java.util.List;

public class RoleList implements Routes {

    @Override
    public void launch(ContextFrame context) {
        JPanel body = context.getApp().getContext();

        Toast toastr = new Toast(body);

        JPanel panel = new JPanel(new GridLayout(3,1, 50, 50));

        DataTable roleModel = new RoleTableModel();

        JTable roleTable = new JTable(roleModel);

        JTableHeader th = roleTable.getTableHeader();
        TableColumnModel tcm = th.getColumnModel();

        TableColumn tc = tcm.getColumn(0);
        tc.setHeaderValue( "ID" );

        TableColumn tc4 = tcm.getColumn(3);
        tc4.setHeaderValue( "Active" );

        TableColumn tc2 = tcm.getColumn(4);
        tc2.setHeaderValue( "Nombre des utilisateurs" );

        JScrollPane sp = new JScrollPane(roleTable);

        JPanel controllers = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton edit = new JButton("Modifier");
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int selectedRow = roleTable.getSelectedRow();
                if(roleTable.isRowSelected(selectedRow)) {
                    JDialog dialog = new JDialog(context.frame());
                    dialog.setSize(800, 700);
                    dialog.setPreferredSize(dialog.getSize());

                    Map roleSelected = roleModel.getDataSource().get(selectedRow);
                    System.out.println(roleSelected);

                    JPanel pan = new JPanel(new BorderLayout(10, 10));
                    JLabel header = new MenuItem("Modifier un role");
                    pan.add(header, BorderLayout.PAGE_START);

                    JPanel body = new JPanel(new GridLayout(3, 1, 40, 40));

                    JPanel form = new JPanel(new GridLayout(3, 1, 20, 20));

                    JTextField abbr = new JTextField(5);
                    abbr.setText(roleSelected.get("abbreviation").toString());
                    abbr.setBorder(BorderFactory.createTitledBorder("Abbreviation"));
                    form.add(abbr);

                    JTextField label = new JTextField(5);
                    label.setText(roleSelected.get("designation").toString());
                    label.setBorder(BorderFactory.createTitledBorder("désignation"));
                    form.add(label);


                    JCheckBox active = new JCheckBox("Role activé");
                    active.setSelected((Boolean) roleSelected.get("enabled"));
                    form.add(active);

                    body.add(form);

                    DualListBox dualBox = new DualListBox("access");

                    List<Map> accessList = RoleRequests.fetchAccessRoleList(roleSelected);
                    List<Map> accessible = new ArrayList<>();
                    List<Map> notAccessible = new ArrayList<>();

                    for (Map item: accessList) {
                        Boolean access = (Boolean) item.get("access");
                        if(access) {
                            accessible.add(item);
                        } else
                            notAccessible.add(item);
                    }

                    dualBox.addDestinationElements(accessible.toArray(new Map[0]));
                    dualBox.addSourceElements(notAccessible.toArray(new Map[0]));

                    dualBox.setRenderer(new DefaultListCellRenderer() {
                        @Override
                        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                            if(value instanceof Map) {
                                Map item = (Map) value;
                                setText(item.get("name").toString());
                            }
                            return this;
                        }
                    });

                    JPanel dualContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    dualContainer.add(dualBox);
                    body.add(dualContainer);

                    JPanel submit = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    JButton done = new JButton("Soumettre");

                    done.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            if(!abbr.getText().isEmpty() || !label.getText().isEmpty()) {
                                Map<String, Object> formData = new HashMap<>();
                                formData.put("roleId", roleSelected.get("roleId"));
                                formData.put("abbreviation", abbr.getText());
                                formData.put("designation", label.getText());
                                formData.put("enabled", active.isSelected());

                                List<Map> accessData = new ArrayList<>();
                                Iterator source = dualBox.sourceIterator();
                                Iterator dest = dualBox.destinationIterator();

                                while (source.hasNext()){
                                    accessData.add((Map) source.next());
                                }
                                while (dest.hasNext()){
                                    accessData.add((Map) dest.next());
                                }

                                Map data = Map.of("role", formData, "accessList", accessData);
                                Boolean response = RoleRequests.updateRole(data);
                                if(response) {
                                    toastr.success("Role est bien modifié !");
                                } else
                                    toastr.error("Il y a un erreur !");
                            } else {
                                toastr.error("Il y a un erreur dans formulaire !");
                            }
                        }
                    });

                    submit.add(done);
                    body.add(submit);

                    pan.add(body, BorderLayout.CENTER);
                    dialog.setContentPane(pan);
                    dialog.setLocationRelativeTo(panel);
                    dialog.setVisible(true);
                    dialog.pack();
                } else {
                    toastr.warn("Selectionner un role dans tableau");
                }
            }
        });
        controllers.add(edit);

        JButton view = new JButton("Details");
        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int selectedRow = roleTable.getSelectedRow();
                if(roleTable.isRowSelected(selectedRow)) {
                    JDialog dialog = new JDialog(context.frame());
                    dialog.setSize(800, 700);
                    dialog.setPreferredSize(dialog.getSize());

                    Map roleSelected = roleModel.getDataSource().get(selectedRow);
                    List<Map> users = UserRequests.all();
                    List<String[]> usersRole = new ArrayList<>();
                    for (Map user: users) {
                        Map roleHasUser = (Map) user.get("role");
                        if(roleHasUser.get("roleId") == roleSelected.get("roleId")) {
                            usersRole.add(new String[]{
                                    user.get("userUId").toString(),
                                    user.get("name").toString()
                            });
                        }
                    }
                    String[][] rows = new String[usersRole.size()][];
                    rows = usersRole.toArray(rows);
                    String[] cols = { "ID", "Nom" };

                    JPanel pan = new JPanel(new BorderLayout(10, 10));
                    JLabel header = new MenuItem("Voir les details d'un role");
                    pan.add(header, BorderLayout.PAGE_START);

                    JPanel body = new JPanel(new GridLayout(3, 1, 40, 40));

                    System.out.println(roleSelected);

                    String[][] role = { { "Abbreviation", roleSelected.get("abbreviation").toString() }, { "Designation", roleSelected.get("designation").toString() }, { "Numero d'utilisateur", roleSelected.get("usersNumber").toString() } };
                    JTable roleInfo = new JTable(role, new String[]{ " ", " "});
                    body.add(roleInfo);

                    JPanel access = new JPanel(new GridLayout(1, 2, 40, 40));
                    DefaultListModel canAccess = new DefaultListModel();
                    DefaultListModel canNotAccess = new DefaultListModel();

                    List<Map> accessList = RoleRequests.fetchAccessRoleList(roleSelected);
                    for (Map item: accessList) {
                        Object label = item.get("name");
                        if((Boolean) item.get("access"))
                            canAccess.addElement(label);
                        else
                            canNotAccess.addElement(label);
                    }

                    JList box1 = new JList(canAccess);
                    JScrollPane pm = new JScrollPane(box1);
                    pm.setBorder(BorderFactory.createTitledBorder("Accessible"));
                    box1.setEnabled(false);
                    box1.setBackground(new Color(230, 255, 233));
                    access.add(pm);

                    JList box2 = new JList(canNotAccess);
                    JScrollPane pn = new JScrollPane(box2);
                    pn.setBorder(BorderFactory.createTitledBorder("Non Accessible"));
                    box2.setEnabled(false);
                    box2.setBackground(new Color(255, 231, 230));
                    access.add(pn);


                    body.add(access);

                    JTable usersTable = new JTable(rows, cols);
                    JScrollPane scrollPane = new JScrollPane(usersTable);
                    scrollPane.setBorder(BorderFactory.createTitledBorder("Liste des utilisateurs pour role"));
                    body.add(scrollPane);

                    pan.add(body, BorderLayout.CENTER);

                    dialog.setContentPane(pan);
                    dialog.setLocationRelativeTo(panel);
                    dialog.setVisible(true);
                    dialog.pack();
                }
            }
        });
        controllers.add(view);

        JPanel form = new JPanel(new GridLayout(2, 2));
        JTextField name = new JTextField(10);
        JTextField abbr = new JTextField(10);
        JTextField label = new JTextField(10);
        JButton insert = new JButton("inserer");

        form.add(name);
        form.add(abbr);
        form.add(label);
        form.add(insert);

        panel.add(sp);
        panel.add(controllers);
        panel.add(form);

        body.add(panel);
        body.setVisible(true);
    }
}
