package edu.episen.si.ing1.pds.client.swing.cards.models;

import edu.episen.si.ing1.pds.client.swing.cards.card.CardRequests;

import java.util.List;
import java.util.Map;

public class CardTableModel extends DataTable {
    List<Map> cardList;

    public CardTableModel() {
        super();
        cardList = CardRequests.fetchCardList();
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
            value = "-";
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

    public Boolean addData(Map data) {
        return CardRequests.inserCard(data);
    }


    public List<Map> getDataSource() {
        return cardList;
    }
}
