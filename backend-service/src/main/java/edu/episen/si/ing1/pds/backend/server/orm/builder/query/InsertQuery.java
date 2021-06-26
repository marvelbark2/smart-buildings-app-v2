package edu.episen.si.ing1.pds.backend.server.orm.builder.query;

import edu.episen.si.ing1.pds.backend.server.orm.builder.DbColumn;
import edu.episen.si.ing1.pds.backend.server.orm.builder.DbTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

public class InsertQuery {
    private final Logger logger = LoggerFactory.getLogger(InsertQuery.class.getName());
    private DbTable table;
    private String sql = "INSERT INTO %s VALUES (%s)";

    public InsertQuery into(DbTable table) {
        this.table = table;
        return this;
    }
    public InsertQuery value(DbColumn col) {
        table.addCol(col);
        return this;
    }
    public InsertQuery value(DbColumn... col) {
        for (DbColumn c: col) {
            table.addCol(c);
        }
        return this;
    }

    public String build() {
        String tableCol = "%s(%s)";
        tableCol = String.format(tableCol, table.getTabName(), colBuilder());
        sql = String.format(sql, tableCol, colVBuilder());
        return sql;
    }
    private String colBuilder() {
        StringBuilder builder = new StringBuilder();
        for (DbColumn col: table.getTabCols()) {
            String colName = col.getColName();
            builder.append(colName);
            builder.append(", ");
        }
        builder.deleteCharAt(builder.length() - 2);
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    private String colVBuilder() {
        StringBuilder builder = new StringBuilder();
        for (DbColumn col: table.getTabCols()) {
            Object colValue = col.getColValue();
            builder.append(colValue);
            builder.append(", ");
        }
        builder.deleteCharAt(builder.length() - 2);
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}
