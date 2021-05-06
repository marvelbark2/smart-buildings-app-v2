package edu.episen.si.ing1.pds.client.swing.cards.card.listeners;

import edu.episen.si.ing1.pds.client.swing.cards.ComponentRegister;
import edu.episen.si.ing1.pds.client.swing.cards.ContextFrame;
import edu.episen.si.ing1.pds.client.swing.cards.card.CardRequests;
import edu.episen.si.ing1.pds.client.swing.cards.card.dialogs.CardUpdateDialog;
import edu.episen.si.ing1.pds.client.swing.cards.models.CardTableModel;
import edu.episen.si.ing1.pds.client.swing.cards.models.DataTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

public class CardButtonListener implements ActionListener {
    ComponentRegister register = ComponentRegister.Instance;
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        if(source.equals(register.getButton("card_edit"))) {
            JTable table = register.getTable("card_table");
            ContextFrame context = (ContextFrame) register.getContainer("context");
            int selectedRow = table.getSelectedRow();
                if (table.isRowSelected(selectedRow)) {
                    DataTable cardDataTable = register.getDataTable("card_dataTable");
                    List<Map> cardList =  cardDataTable.getDataSource();
                    String cardIdRow = cardList.get(selectedRow).get(cardDataTable.getColumnName(0)).toString();
                    int cardId = Integer.parseInt(cardIdRow);
                    Map<String, Object> responseBody = CardRequests.fetchCardById(cardId);
                    Map<String, Object> data = (Map<String, Object>) responseBody.get("card");
                    CardUpdateDialog cardUpdateDialog =  new CardUpdateDialog(context, data);
                    JDialog dialog = cardUpdateDialog.getDialog();
                    dialog.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            table.setModel(new CardTableModel());
                        }
                    });
                }
                else {
                   // toast.warn("Selectionnez une ligne dans le tableau");
                    System.out.println("Erreur! Il y a un problème");
                }
        }
    }
}
