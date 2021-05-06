package edu.episen.si.ing1.pds.client.swing.cards.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.episen.si.ing1.pds.client.swing.cards.ContextFrame;
import edu.episen.si.ing1.pds.client.swing.cards.card.CardRequests;
import edu.episen.si.ing1.pds.client.swing.cards.card.dialogs.CardReadDialog;
import edu.episen.si.ing1.pds.client.swing.cards.card.dialogs.CardUpdateDialog;
import edu.episen.si.ing1.pds.client.swing.cards.card.dialogs.Dialogs;
import edu.episen.si.ing1.pds.client.swing.global.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class UserDetails extends JDialog {
    public UserDetails(ContextFrame context, Map user) {
        super(context.frame());
        ObjectMapper mapper = new ObjectMapper();
        setPreferredSize(new Dimension(1000, 800));
        setLocationRelativeTo(null);
        JsonNode userInfo = UserRequests.findUserInfo(user);

        JPanel pane = new JPanel(new BorderLayout(50, 50));
        JPanel header = new JPanel(new BorderLayout());
        JLabel title = new MenuItem("Information d'un utilisateur");
        header.add(title, BorderLayout.CENTER);

        JPanel body = new JPanel(new GridLayout(2, 1));

        String[][] userRow = {
                {"ID", userInfo.get("user").get("userUId").asText()},
                {"Nom", userInfo.get("user").get("name").asText()},
                {"Role", userInfo.get("user").get("role").get("designation").asText()}
        };
        JPanel userInfoPanel = new JPanel(new BorderLayout());
        userInfoPanel.setBorder(BorderFactory.createTitledBorder("Info d'utilisateur"));
        JTable table = new JTable(userRow, new String[]{" ", " "});
        userInfoPanel.add(table);
        body.add(userInfoPanel);

        if (userInfo.has("card")) {
            JPanel cardInfoPanel = new JPanel(new BorderLayout());
            cardInfoPanel.setBorder(BorderFactory.createTitledBorder("Carte d'utilisateur"));

            String[][] cardRow = {
                    {
                            userInfo.get("card").get("cardId").asText(),
                            userInfo.get("card").get("cardUId").asText(),
                            userInfo.get("card").get("expirable").asBoolean() ? "Oui" : "Non",
                            userInfo.get("card").get("expiredDate").asText() != null ? "Infini" : userInfo.get("card").get("expiredDate").asText(),
                    }
            };
            String[] cardCol = {"ID", "Matricule", "Provisoire", "Date d'expiration"};
            JTable cardTable = new JTable(cardRow, cardCol) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            JScrollPane scrollPane = new JScrollPane(cardTable);
            Boolean active = userInfo.get("card").get("active").asBoolean();
            cardTable.setBackground(active ? null : Color.red);
            cardTable.setForeground(active ? Color.BLACK : Color.white);
            scrollPane.setOpaque(false);
            cardInfoPanel.add(scrollPane, BorderLayout.CENTER);

            JPanel toolsCard = new JPanel(new GridLayout(2, 2));

            JButton activeBtn = new JButton("Active");
            toolsCard.add(activeBtn);

            JButton inactiveBtn = new JButton("Desactive");
            toolsCard.add(inactiveBtn);

            JButton edit =  new JButton("Modifier");
            edit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        Map<String, Object> card = mapper.treeToValue(userInfo.get("card"), HashMap.class);
                        Dialogs cardUpdateDialog = new CardUpdateDialog(context, card);
                        cardUpdateDialog.getDialog();

                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                }
            });

            toolsCard.add(edit);

            JButton deleteBtn = new JButton("Supprimer");
            deleteBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        Map<String, Object> card = mapper.treeToValue(userInfo.get("card"), HashMap.class);
                        Boolean deleteCard = CardRequests.deleteCard(card);
                        if(deleteCard) {
                            dispose();
                        }
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            });
            toolsCard.add(deleteBtn);

            JButton read = new JButton("Voir");
            read.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        Map<String, Object> card = mapper.treeToValue(userInfo.get("card"), HashMap.class);
                        Dialogs cardDialog = new CardReadDialog(context, card);
                        cardDialog.getDialog();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            });
            toolsCard.add(read);

            toolsCard.add(new JButton("Perdue"));
            toolsCard.setBorder(BorderFactory.createTitledBorder("Outils"));
            cardInfoPanel.add(toolsCard, BorderLayout.LINE_END);

            body.add(cardInfoPanel);
        }


        pane.add(header, BorderLayout.PAGE_START);
        pane.add(body, BorderLayout.CENTER);


        setContentPane(pane);
        pack();

        setLocationRelativeTo(context.frame());
        setVisible(true);
    }
}
