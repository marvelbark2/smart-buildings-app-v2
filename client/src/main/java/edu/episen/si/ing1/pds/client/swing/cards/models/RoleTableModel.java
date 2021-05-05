package edu.episen.si.ing1.pds.client.swing.cards.models;

import edu.episen.si.ing1.pds.client.swing.cards.roles.RoleRequests;

import java.util.List;
import java.util.Map;

public class RoleTableModel extends DataTable {
    private List<Map> roleList;

    public RoleTableModel() {
        roleList = RoleRequests.fetchRoleList();
    }

    @Override
    public List<Map> getDataSource() {
        return roleList;
    }

    @Override
    public Boolean addData(Map data) {
        return null;
    }

    @Override
    public int getRowCount() {
        return roleList.size();
    }

    @Override
    public int getColumnCount() {
        return roleList.get(0).size();
    }

    @Override
    public Object getValueAt(int i, int i1) {
        Object data = roleList.get(i).get(getColumnName(i1));
        if(data instanceof Boolean) {
            Boolean active = (Boolean) data;
            if(active)
                return "Oui";
            else
                return "Non";
        }
        return data;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return (String) roleList.get(0).keySet().toArray()[column];
    }
}
