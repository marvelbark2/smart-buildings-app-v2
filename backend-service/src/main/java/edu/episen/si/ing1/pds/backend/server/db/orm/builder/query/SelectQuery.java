package edu.episen.si.ing1.pds.backend.server.db.orm.builder.query;

import edu.episen.si.ing1.pds.backend.server.db.orm.builder.DbColumn;
import edu.episen.si.ing1.pds.backend.server.db.orm.builder.DbTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class SelectQuery {
    private final Logger logger = LoggerFactory.getLogger(SelectQuery.class.getName());
    private DbTable table;
    private final Set<DbColumn> orderBy = new LinkedHashSet<>();
    private int limit = 0;

    public SelectQuery from(DbTable table) {
        this.table = table;
        return this;
    }
    public SelectQuery select(DbColumn col) {
        if(this.table != null){
            table.addCol(col);
            return this;
        } else {
            logger.warn("From what table do you want select from ?");
            return this;
        }
    }
    public SelectQuery select(DbColumn... cols) {
        if(this.table != null){
            for (DbColumn col: cols) {
                table.addCol(col);
            }
            return this;
        } else {
            logger.warn("From what table do you want select from ?");
            return this;
        }
    }

    public SelectQuery orderBy(DbColumn col) {
        orderBy.add(col);
        return this;
    }
    public SelectQuery orderBy(DbColumn... col) {
        orderBy.addAll(Arrays.stream(col).collect(Collectors.toList()));
        return this;
    }
    public SelectQuery limit(int limit) {
        this.limit = limit;
        return this;
    }

    public String build() {
        String query = "SELECT %s FROM %s";
        String sql = String.format(query, colBuilder(), table.getTabName());
        if(orderBy.size() > 0 && limit > 0){
            String orderBySql = orderByBuilder();
            query = orderBySql + " LIMIT %d";
            query = String.format(query, limit);
            sql += " "+query;
            return sql;
        } else if(orderBy.size() > 0) {
            String orderBySql = orderByBuilder();
            query = "ORDER BY %s";
            query = String.format(query, orderBySql);
            sql += " "+query;
        } else if(limit > 0) {
            query = "LIMIT %d";
            sql += String.format(query, limit);
            return sql;
        }
        return sql;
    }

    private String orderByBuilder() {
        String orderBY = "ORDER BY %s";
        StringBuilder builder = new StringBuilder();
        for (DbColumn col: orderBy) {
            builder.append(col.getColName());
            builder.append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        return String.format(orderBY, builder);
    }

    private String colBuilder() {
        if(table.getTabCols().size() == 0) {
            return "*";
        } else {
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
    }
}
