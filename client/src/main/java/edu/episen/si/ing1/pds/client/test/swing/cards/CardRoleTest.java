package edu.episen.si.ing1.pds.client.test.swing.cards;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.network.SocketFacade;
import edu.episen.si.ing1.pds.client.test.swing.Routes;

import javax.swing.*;
import java.awt.*;
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
        JFrame frame = context.getFrame();
        frame.setTitle("Tester Permission");

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
        frame.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
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

                System.out.println(line);

                Response response = mapper.readValue(line, Response.class);
                if(response.getEvent().equals(event)) {
                    List<Map<String, String>> data = (List) response.getMessage();
                    System.out.println(data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
