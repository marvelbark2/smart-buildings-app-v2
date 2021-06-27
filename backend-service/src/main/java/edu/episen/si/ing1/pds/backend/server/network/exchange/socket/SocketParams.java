package edu.episen.si.ing1.pds.backend.server.network.exchange.socket;

import java.nio.channels.SelectionKey;
import java.sql.Connection;

public class SocketParams {
    private final SelectionKey key;
    private boolean encrypted;
    private boolean closed = false;
    private static Connection connection;

    public SocketParams(SelectionKey key, boolean encrypted) {
        this.key = key;
        this.encrypted = encrypted;
    }

    public SocketParams(SelectionKey key) {
        this.key = key;
        this.encrypted = false;
    }

    public SelectionKey getKey() {
        return key;
    }

    public boolean isEncrypted() {
        return encrypted;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean isClosed() {
        return closed;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        SocketParams.connection = connection;
    }

    @Override
    public String toString() {
        return "SocketParams{" +
                "key=" + key +
                ", encrypted=" + encrypted +
                ", closed=" + closed +
                '}';
    }
}
