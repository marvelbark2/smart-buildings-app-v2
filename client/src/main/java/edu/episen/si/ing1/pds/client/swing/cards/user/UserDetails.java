package edu.episen.si.ing1.pds.client.swing.cards.user;

import com.fasterxml.jackson.databind.JsonNode;
import edu.episen.si.ing1.pds.client.swing.global.MenuItem;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Map;

public class UserDetails extends JDialog {
    public UserDetails(JFrame frame, Map user) {
        super(frame);
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
            toolsCard.add(new JButton("Active"));
            toolsCard.add(new JButton("Desactive"));
            toolsCard.add(new JButton("Modifier"));
            toolsCard.add(new JButton("Supprimer"));
            toolsCard.add(new JButton("Voir"));
            toolsCard.add(new JButton("Perdue"));
            toolsCard.setBorder(BorderFactory.createTitledBorder("Outils"));
            cardInfoPanel.add(toolsCard, BorderLayout.LINE_END);

            body.add(cardInfoPanel);
        }


        pane.add(header, BorderLayout.PAGE_START);
        pane.add(body, BorderLayout.CENTER);


        setContentPane(pane);
        pack();

        setLocationRelativeTo(frame);
        setVisible(true);
    }
}
