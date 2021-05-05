package edu.episen.si.ing1.pds.client.swing.cards.models;

import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.swing.cards.user.UserRequests;
import edu.episen.si.ing1.pds.client.utils.Utils;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Map;

public class UserTableModel extends DataTable {

    private List<Map> userList;

    public UserTableModel() {
        userList = UserRequests.all();
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
        Object element =  userList.get(rowIndex).get(getColumnName(columnIndex));
        if(element instanceof Map) {
            Map role = (Map) element;
            return role.get("designation");
        } else {
            return element;
        }
    }

    @Override
    public String getColumnName(int column) {
        return (String) userList.get(0).keySet().toArray()[column];
    }

    public Boolean addData(Map data) {
        Request request = new Request();
        request.setEvent("user_insert");
        request.setData(data);
        Response response = Utils.sendRequest(request);
        Boolean isInserted = (Boolean) response.getMessage();
        return isInserted;
    }

    public List<Map> getDataSource() {
        return userList;
    }
}
