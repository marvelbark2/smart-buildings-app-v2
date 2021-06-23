package edu.episen.si.ing1.pds.client.swing.cards.models;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Map;

abstract public class DataTable extends AbstractTableModel {
    public abstract List<Map> getDataSource();
    public abstract Boolean addData(Map data);
}
