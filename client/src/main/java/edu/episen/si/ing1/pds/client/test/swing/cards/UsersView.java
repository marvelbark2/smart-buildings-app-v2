package edu.episen.si.ing1.pds.client.test.swing.cards;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.test.swing.Routes;
import edu.episen.si.ing1.pds.client.test.swing.cards.models.UserTableModel;
import edu.episen.si.ing1.pds.client.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class UsersView implements Routes {
    private JTable table;
    private String[][] tableData;

    private final Logger logger = LoggerFactory.getLogger(UsersView.class.getName());

    public UsersView() {
        getData();
    }

    @Override
    public void launch(ContextFrame context) {
        JPanel frame = context.getApp().getContext();

        JPanel panel = new JPanel();

        UserTableModel model = new UserTableModel();
        table = new JTable(model);

        JScrollPane jScrollPane = new JScrollPane(table);
        panel.add(jScrollPane);

        JButton button = new JButton("Refresh Table");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.dataChanged(); // Repaint one cell.
            }
        });

        panel.add(button);

        frame.add(panel);
        frame.setVisible(true);
//        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }

    private void getData() {
        Request request = new Request();
        request.setEvent("user_list");
        List<Map> data = (List<Map>) Utils.sendRequest(request).getMessage();
        logger.info(data.get(data.size() - 1).toString());
        String[][] arr = new String[data.size()][data.get(0).size()];
        int i = 0;
        for (Map map : data) {
            int w = 0;
            for (Object key : map.keySet()) {
                if (map.get(key) != null) {
                    if (map.get(key) instanceof Map)
                        arr[i][w] = ((Map<?, ?>) map.get(key)).get("name").toString();
                    else
                        arr[i][w] = map.get(key).toString();
                    w++;
                } else {
                    arr[i][w] = "Infini";
                    w++;
                }
            }
            i++;
        }
        tableData = arr;
    }
}
