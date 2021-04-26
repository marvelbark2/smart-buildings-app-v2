package edu.episen.si.ing1.pds.client.swing;

import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.network.SocketConfig;
import edu.episen.si.ing1.pds.client.utils.Utils;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class MappingTest {
    public static void main(String[] args) {
        SocketConfig.Instance.setEnv(true);
        JFrame frame = new JFrame("card_access_list");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setPreferredSize(frame.getSize());

        JPanel panel = new JPanel();
        Request request = new Request();

        request.setEvent("card_access_list");
        request.setData(Map.of("serialId", "NLLMY-4DJF-8HPQ-MQHW-SGX"));
        Response response = Utils.sendRequest(request);
        List<Map> data = (List<Map>) response.getMessage();
        String[][] dataTable = new String[data.size()][];
        String[] cols = { "id", "title", "type", "acces" };
        int i = 0;
        for (Map e: data) {
            dataTable[i] = new String[]{ e.get("id").toString(), e.get("title").toString(), e.get("type").toString(), e.get("accessible").toString()};
            i++;
        }
        JTable table = new JTable(dataTable, cols);
        JScrollPane pane = new JScrollPane(table);
        panel.add(pane);

        frame.setContentPane(panel);
        frame.pack();
    }
}
