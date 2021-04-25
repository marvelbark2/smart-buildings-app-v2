package edu.episen.si.ing1.pds.client.test.sockets;

import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.network.SocketClient;
import edu.episen.si.ing1.pds.client.network.SocketConfig;
import edu.episen.si.ing1.pds.client.utils.Utils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class MappingTest {
    public static void main(String[] args) {
        SocketConfig.Instance.setEnv(true);
        JFrame frame = new JFrame("Test");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setPreferredSize(frame.getSize());

        JPanel panel = new JPanel();
        Request request = new Request();

        request.setEvent("test");
        Response response = Utils.sendRequest(request);
        List<Map> data = (List<Map>) response.getMessage();
        for (Map e: data) {
            JLabel label = new JLabel(e.get("abbreviation").toString());
            label.setBorder(new LineBorder(Color.BLUE));
            panel.add(label);
        }

        frame.setContentPane(panel);
        frame.pack();
    }
}
