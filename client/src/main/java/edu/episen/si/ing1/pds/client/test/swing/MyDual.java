package edu.episen.si.ing1.pds.client.test.swing;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class MyDual extends JFrame {
    public MyDual() {
        super("Testing");
        List<Map> data = List.of(
                Map.of("id", "1222", "name", "d", "access", false,"modified", false),
                Map.of("id", "1321", "name", "e", "access", true,"modified", false),
                Map.of("id", "2251", "name", "s", "access", false,"modified", false),
                Map.of("id", "5333", "name", "a", "access", true,"modified", false)
        );
        Vector<Map> accessible = new Vector<>();
        Vector<Map> not = new Vector<>();


        for (Map map: data) {
            if((Boolean) map.get("access")) {
                accessible.add(map);
            } else {
                not.add(map);
            }
        }
        JPanel panel = new JPanel(new GridLayout(2, 2, 50, 50));
        JList access = new JList(accessible);
        access.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Map)
                    setText(((Map<String, String>) value).get("name"));

                return this;
            }
        });

        JList notAccess = new JList(not);

        JPanel buttons = new JPanel(new GridLayout(2, 1, 10, 10));
        JButton add = new JButton("add");
        JButton remove = new JButton("remove");

        buttons.add(add);
        buttons.add(remove);

        panel.add(access);
        panel.add(buttons);
        panel.add(notAccess);

        setSize(400, 400);
        setContentPane(panel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new MyDual();
    }
}
