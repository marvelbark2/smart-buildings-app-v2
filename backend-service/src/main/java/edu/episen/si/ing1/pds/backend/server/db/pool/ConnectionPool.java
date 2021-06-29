package edu.episen.si.ing1.pds.backend.server.db.pool;

import java.sql.Connection;

public class ConnectionPool {
    private final Connection connection;
    private final boolean tmp;
    private boolean closed = false;

    public ConnectionPool(Connection connection, boolean tmp) {
        this.connection = connection;
        this.tmp = tmp;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isTmp() {
        return tmp;
    }

    public boolean isClosed() {
        return closed;
    }

    public void close() throws Exception {
        connection.close();
        closed = true;
    }

    @Override
    public String toString() {
        return "ConnectionPool{" +
                "connection=" + connection +
                ", tmp=" + tmp +
                ", closed=" + closed +
                '}';
    }
}
