package edu.episen.si.ing1.pds.backend.server.db.orm.builder;

import edu.episen.si.ing1.pds.backend.server.db.orm.builder.query.InsertQuery;
import edu.episen.si.ing1.pds.backend.server.db.orm.builder.query.SelectQuery;

public class Builder {
    private DbTable table;

    public Builder(DbTable table) {
        this.table = table;
    }

    public String select() {
        String sql = "SELECT %s FROM %s";
        if(table.getTabCols().size() == 0) {
            return String.format(sql, "*", table.getTabName());
        } else {
            StringBuilder builder = new StringBuilder();
            for (DbColumn col: table.getTabCols()) {
                String colName = col.getColName();
                builder.append(colName);
                builder.append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
            return String.format(sql, builder, table.getTabName());
        }
    }

    public static SelectQuery selectBuilder() {
        return new SelectQuery();
    }
    public static InsertQuery insertBuilder() {
        return new InsertQuery();
    }
}
