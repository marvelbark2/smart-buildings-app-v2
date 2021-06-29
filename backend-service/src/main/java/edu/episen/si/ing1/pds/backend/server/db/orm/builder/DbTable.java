package edu.episen.si.ing1.pds.backend.server.db.orm.builder;

import java.util.LinkedHashSet;
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
