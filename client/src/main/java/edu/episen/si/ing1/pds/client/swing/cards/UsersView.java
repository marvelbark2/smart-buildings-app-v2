package edu.episen.si.ing1.pds.client.swing.cards;

import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.swing.cards.models.UserTableModel;
import edu.episen.si.ing1.pds.client.swing.global.shared.toast.Toast;
import edu.episen.si.ing1.pds.client.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class UsersView implements Routes {
    private JTable table;
    private final Logger logger = LoggerFactory.getLogger(UsersView.class.getName());
    private Toast toast;

    @Override
    public void launch(ContextFrame context) {
        JPanel frame = context.getApp().getContext();

        toast = new Toast(frame);
        frame.setLayout(new GridLayout(3,1, 100, 100));

        UserTableModel model = new UserTableModel();
        table = new JTable(model);

        table.setVisible(true);

        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setPreferredSize( new Dimension(400, 600) );


        JButton button = new JButton("Refresh Table");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });

        JButton edit = new JButton("Modifier");
        JButton delete = new JButton("Supprimer");
        JButton read = new JButton("details");

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actionPanel.add(edit, FlowLayout.LEFT);
        actionPanel.add(delete, FlowLayout.CENTER);
        actionPanel.add(read, FlowLayout.RIGHT);

        JPanel formPanel = new JPanel();
        JTextField nameField = new JTextField(10);
        formPanel.add(new JLabel("Nom"));
        formPanel.add(nameField);

        JPanel buttonPanel = new JPanel(new BorderLayout());

        JButton insert = new JButton("Soumettre");
        insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map data = Map.of("name", nameField.getText());
                Boolean response = model.addData(data);
                if(response) {
                    toast.success("Utilisateur est bien enregister");
                    refresh();
                }

            }
        });

        buttonPanel.add(button, BorderLayout.PAGE_START);
        buttonPanel.add(actionPanel, BorderLayout.CENTER);

        frame.add(jScrollPane);
        formPanel.add(insert);
        frame.add(buttonPanel);
        frame.add(formPanel);

        frame.setVisible(true);
    }
    private void refresh() {
        table.setModel(new UserTableModel());
        table.invalidate();
        table.validate();
        table.repaint();
        toast.success("Tableau est bien rafraîchi");
    }
}
