package edu.episen.si.ing1.pds.client.test.swing.cards;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.network.SocketFacade;
import edu.episen.si.ing1.pds.client.test.swing.Routes;
import edu.episen.si.ing1.pds.client.test.swing.toast.Toast;
import edu.episen.si.ing1.pds.client.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class CardView implements Routes {
    private final ObjectMapper mapper = new ObjectMapper();
    private String[][] tableData;
    private PrintWriter writer;
    private BufferedReader reader;
    private final Logger logger = LoggerFactory.getLogger(CardView.class.getName());
    private Toast toast;

    public CardView() {

    }

    private void getCardList() {
        try {
            Request request = new Request();
            request.setEvent("card_list");
            String requestSerialized = mapper.writeValueAsString(request);
            writer.println(requestSerialized);
            while (true) {
                String req = reader.readLine();
                if (req == null)
                    break;

                Response response = mapper.readValue(req, Response.class);
                if (response.getMessage().equals("end"))
                    break;
                if (response.getEvent().equals("card_list")) {
                    List<Map<String, Object>> data = (List) response.getMessage();
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void launch(ContextFrame context) {
        JPanel frame = context.getApp().getContext();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        toast = new Toast(panel);
        try {
            Socket socket = SocketFacade.Instance.getSocket();
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String[] cols = {"ID", "Matricule", "Provisoire", "Date d'expiration", "user"};
            getCardList();
            DefaultTableModel tableModel = new DefaultTableModel(tableData, cols);
            JTable table = new JTable(tableModel);
            JTableHeader header = table.getTableHeader();
            header.setBackground(Color.black);
            header.setForeground(Color.white);
            JScrollPane sp = new JScrollPane(table);

            ListSelectionModel select = table.getSelectionModel();
            JPanel btnPanel = new JPanel(new GridLayout(1, 3, 20, 30));

            btnPanel.setBorder(BorderFactory.createTitledBorder("Operations"));


            JButton edit = new JButton("Modifier");
            edit.setPreferredSize(new Dimension(40, 40));
            JButton delete = new JButton("Supprimer");
            delete.setPreferredSize(new Dimension(40, 40));
            JButton details = new JButton("details");
            details.setPreferredSize(new Dimension(40, 40));

            delete.addActionListener((e) -> {
                int selectedRow = table.getSelectedRow();
                if (table.isRowSelected(selectedRow)) {
                    Map<String, String> hm = new HashMap<>();
                    for (int i = 0; i < cols.length; i++) {
                        hm.put(cols[i], tableData[selectedRow][i]);
                    }
                    try {
                        Request request = new Request();
                        request.setEvent("card_delete");
                        request.setData(hm);
                        writer.println(mapper.writeValueAsString(request));
                        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                            Response response = mapper.readValue(line, Response.class);
                            if (response.getMessage().equals("end"))
                                break;
                            if (response.getEvent().equals("card_delete")) {
                                toast.success("La suppression est bien faite");
                                logger.info("Data Deleted");
                                logger.info(response.toString());
                                table.clearSelection();
                            }
                        }
                    } catch (Exception jsonProcessingException) {
                        jsonProcessingException.printStackTrace();
                    }
                } else {
                    toast.error("Selectionner une ligne pour lui supprimer");
                    logger.error("Select a row");
                }
            });

            details.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = table.getSelectedRow();
                    if (table.isRowSelected(selectedRow)) {
                        Request request = new Request();
                        request.setEvent("card_byid");
                        request.setData(Map.of("id", Integer.parseInt(tableData[selectedRow][0])));

                        Response response = Utils.sendRequest(request);
                    }

                }
            });

            btnPanel.add(edit, BorderLayout.CENTER);
            btnPanel.add(delete, BorderLayout.CENTER);


            select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            Border blackline = BorderFactory.createTitledBorder("Inserer une carte");
            JPanel formPanel = new JPanel();
            LayoutManager layout = new GridLayout(2, 2);
            formPanel.setLayout(layout);
            formPanel.setBorder(blackline);

            Request requestUserList = new Request();
            requestUserList.setEvent("user_list");
            writer.println(mapper.writeValueAsString(requestUserList));
            List userList = null;

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                Response response = mapper.readValue(line, Response.class);
                if (response.getMessage().equals("end"))
                    break;
                userList = (List) response.getMessage();
            }
            JComboBox comboBox = new JComboBox(new Vector(userList));
            comboBox.setPreferredSize(new Dimension(250, 25));
            comboBox.setSelectedIndex(-1);
            comboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof Map)
                        setText(((Map<String, String>) value).get("name"));
                    if (index == -1 && value == null)
                        setText("Selectionner un utilisateur");

                    return this;
                }
            });

            comboBox.addActionListener(e -> {
                JComboBox self = (JComboBox) e.getSource();
                Map o = (Map) self.getSelectedItem();
            });

            //ComboBox field
            JPanel comboField = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            JLabel comboFieldFor = new JLabel("User: ");
            Border comboBorder = BorderFactory.createTitledBorder("Champ Utilisateur");
            comboField.add(comboFieldFor);
            comboField.add(comboBox);
            comboField.setBorder(comboBorder);
            formPanel.add(comboField);

            //Serial Number flied
            JPanel sNField = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            JLabel sNFieldFor = new JLabel("Matricule: ");
            JTextField snFieldText = new JTextField(20);
            JButton snGenerator = new JButton("Generer 1");
            snGenerator.addActionListener(e -> snFieldText.setText(Utils.generateSerialNumber()));
            Border sNBorder = BorderFactory.createTitledBorder("Champ Matricule");
            sNField.add(sNFieldFor);
            sNField.add(snFieldText);
            sNField.add(snGenerator);
            sNField.setBorder(sNBorder);
            formPanel.add(sNField);

            //tmp card field
            JPanel expirableField = new JPanel(new FlowLayout(FlowLayout.CENTER));
            Border expirableBorder = BorderFactory.createTitledBorder("Expirable");
            expirableField.setBorder(expirableBorder);
            JCheckBox expirable = new JCheckBox("Carte Provisoire ?");
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            JFormattedTextField expirableDate = new JFormattedTextField(format);
            expirableDate.setColumns(12);
            expirableDate.setEnabled(false);
            try {
                expirableDate.commitEdit();
            } catch (ParseException e) {

            }
            expirable.addChangeListener((e) -> expirableDate.setEnabled(expirable.isSelected()));
            expirableField.add(expirable);
            formPanel.add(expirableField);

            // Expire Date Field
            JPanel expireDate = new JPanel(new FlowLayout(FlowLayout.CENTER));
            Border expireDateBorder = BorderFactory.createTitledBorder("Date d'expiration");
            expireDate.setBorder(expireDateBorder);
            JLabel expireDateLabel = new JLabel("Date: ");
            expireDate.add(expireDateLabel);
            expireDate.add(expirableDate);
            formPanel.add(expireDate);

            //Submit Button
            JPanel submit = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton submitButton = new JButton("Soumettre");
            submitButton.setSize(100, 100);
            submitButton.addActionListener((e) -> {
                try {
                    if ((snFieldText.getText() != null && snFieldText.getText().length() >= 20) && (comboBox.getSelectedItem() != null || comboBox.getSelectedIndex() != -1)) {
                        Request insertCardReq = new Request();
                        insertCardReq.setEvent("card_insert");
                        String expireDateValue = "";
                        if (expirable.isSelected())
                            expireDateValue = expirableDate.getText();

                        insertCardReq.setData(Map.of("cardUId", snFieldText.getText(), "expirable", expirable.isSelected(), "expireDate", expireDateValue, "user", comboBox.getSelectedItem()));
                        writer.println(mapper.writeValueAsString(insertCardReq));
                        while (true) {
                            Response response = mapper.readValue(reader.readLine(), Response.class);
                            if (response.getMessage().equals("end"))
                                break;
                            if (response.getEvent().equals("card_insert")) {
                                Boolean isAdded = Boolean.valueOf((Boolean) response.getMessage());
                                if (isAdded) {
                                    new Thread(() -> SwingUtilities.invokeLater(() -> {
                                        table.clearSelection();
                                        getCardList();
                                        table.setModel(tableModel);
                                        tableModel.fireTableDataChanged();
                                    })).start();

                                }
                            }
                        }
                        snFieldText.setText("");
                        expirable.setSelected(false);
                        expirableDate.setText("");
                        comboBox.setSelectedIndex(-1);

                    } else {
                        toast.error("Erreur ! Veuillez remplir la formulaire");
                    }

                } catch (Exception jsonProcessingException) {
                    jsonProcessingException.printStackTrace();
                }
            });
            submit.add(submitButton);

            JButton returnBack = new JButton("Retourner");
            returnBack.addActionListener(e -> {
                context.returnHere();
                panel.setVisible(false);
            });
            Box buttonsPanel = Box.createVerticalBox();
            buttonsPanel.add(returnBack);
            returnBack.setBackground(new Color(2, 117, 216));
            returnBack.setOpaque(true);

            buttonsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);//0.0

            panel.add(buttonsPanel, Component.LEFT_ALIGNMENT);
            panel.add(sp);
            panel.add(btnPanel);
            panel.add(formPanel);
            panel.add(submit);

            frame.add(panel);


//            frame.addWindowListener(new WindowAdapter() {
//                @Override
//                public void windowClosing(WindowEvent e) {
//                    super.windowClosing(e);
//                    try {
//                        writer.close();
//                        reader.close();
//                        socket.close();
//                    } catch (IOException ioException) {
//                        ioException.printStackTrace();
//                    }
//                }
//            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
