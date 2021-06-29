package edu.episen.si.ing1.pds.backend.server.db.orm.builder;

import java.sql.Types;

public class DbColumn {
    private String colName;
    private Types colType;
    private Object colValue;
    private boolean primaryKey;


    public DbColumn(String colName) {
        this.colName = colName;
    }

    public DbColumn(String colName, Object colValue) {
        this.colName = colName;
        this.colValue = colValue;
    }

    public DbColumn(String colName, Types colType) {
        this.colName = colName;
        this.colType = colType;
    }

    public DbColumn(String colName, Types colType, Object colValue) {
        this.colName = colName;
        this.colType = colType;
        this.colValue = colValue;
    }

    public DbColumn(String colName, Types colType, Object colValue, boolean primaryKey) {
        this.colName = colName;
        this.colType = colType;
        this.colValue = colValue;
        this.primaryKey = primaryKey;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public Types getColType() {
        return colType;
    }

    public void setColType(Types colType) {
        this.colType = colType;
    }

    public Object getColValue() {
        return colValue;
    }

    public void setColValue(Object colValue) {
        this.colValue = colValue;
    }
}
