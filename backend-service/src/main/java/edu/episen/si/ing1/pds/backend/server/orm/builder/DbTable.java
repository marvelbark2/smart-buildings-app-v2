package edu.episen.si.ing1.pds.backend.server.orm.builder;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class DbTable {
    private String tabName;
    private final Set<DbColumn> tabCols;

    public DbTable(String tabName) {
        this.tabName = tabName;
        tabCols = new LinkedHashSet<>();
    }

    public String getTabName() {
        return tabName;
    }

    public Set<DbColumn> getTabCols() {
        return tabCols;
    }

    public void addCol(DbColumn col) {
        this.tabCols.add(col);
    }
}
