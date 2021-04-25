package edu.episen.si.ing1.pds.client.swing.cards.models;

import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.utils.Utils;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Map;

public class UserTableModel extends AbstractTableModel {

    private List<Map> userList;

    public UserTableModel() {
        Request request = new Request();
        request.setEvent("user_list");
        userList = (List<Map>) Utils.sendRequest(request).getMessage();
    }

    @Override
    public int getRowCount() {
        return userList.size();
    }

    @Override
    public int getColumnCount() {
        return userList.get(0).keySet().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return userList.get(rowIndex).get(getColumnName(columnIndex));
    }

    @Override
    public String getColumnName(int column) {
        return (String) userList.get(0).keySet().toArray()[column];
    }

    public void dataChanged() {
        new UserTableModel();
        fireTableDataChanged();
    }
}
