package edu.episen.si.ing1.pds.client.swing.cards.models;

import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.swing.global.shared.Ui;
import edu.episen.si.ing1.pds.client.utils.Utils;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class CardTableModel extends AbstractTableModel implements TableCellRenderer {
    List<Map> cardList;

    public CardTableModel() {
        super();
        Request req = new Request();
        req.setEvent("card_list");
        Response response = Utils.sendRequest(req);
        cardList = (List<Map>) response.getMessage();
    }

    @Override
    public int getRowCount() {
        return cardList.size();
    }

    @Override
    public int getColumnCount() {
        return cardList.get(0).keySet().size() - 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = cardList.get(rowIndex).get(getColumnName(columnIndex));
        if(value instanceof Map)
            value = ( (Map) value).get("name");
        else if(value == null)
            value = "Infini";
        else if(value instanceof Boolean) {
            Boolean bool = (Boolean) value;
            if(bool)
                value = "Oui";
            else
                value = "Non";
        }
        return value;
    }

    @Override
    public String getColumnName(int column) {
        return (String) cardList.get(0).keySet().toArray()[column];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);
    }

    public Boolean addData(Request insertCardReq) {
        Response response = Utils.sendRequest(insertCardReq);
        return (Boolean) response.getMessage();
    }


    public List<Map> getDataSource() {
        return cardList;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel text = new JLabel(value.toString());
        text.setFont(Ui.FONT_GENERAL_UI);
        text.setOpaque(true);
        Boolean bool = (Boolean) cardList.get(row).get("active");
        if(!bool)
            text.setBackground(Color.RED);

        return text;
    }
}
