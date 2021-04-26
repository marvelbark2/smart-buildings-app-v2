package edu.episen.si.ing1.pds.client.swing.cards;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.network.SocketFacade;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class CardRoleTest implements Routes {

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void launch(ContextFrame context) {
        JPanel frame = context.getApp().getContext();

        JPanel panel = new JPanel();
        JTextField serialField = new JTextField(25);
        String[] desks = { "Salle 1", "Salle 2", "Salle 3", "Salle 4" };
        DefaultComboBoxModel model = new DefaultComboBoxModel(desks);
        JComboBox comboBox = new JComboBox(model);

        JButton jButton = new JButton("Verifier");


        panel.add(serialField);
        panel.add(comboBox);
        panel.add(jButton);

        frame.add(panel);
        frame.setVisible(true);
    }
    private void getData() {
        try {
            Socket socket = SocketFacade.Instance.getSocket();
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            InputStream in = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            Request request = new Request();
            String event = "user_list";
            request.setEvent(event);
            writer.println(mapper.writeValueAsString(request));
            System.out.println("data Send");
            while (true) {
                String line = reader.readLine();
                if(line == null || line.equals("end"))
                    break;

                if(!line.equals("end"))
                    break;

                Response response = mapper.readValue(line, Response.class);
                if(response.getEvent().equals(event)) {
                    List<Map<String, String>> data = (List) response.getMessage();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
