package edu.episen.si.ing1.pds.backend.server.network.exchange;

import edu.episen.si.ing1.pds.backend.server.network.server.SocketServer;

import java.nio.channels.SelectionKey;

public class SocketParams {
    private final SelectionKey key;
    private boolean encrypted;

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

    @Override
    public String toString() {
        return "SocketParams{" +
                "key=" + key +
                ", encrypted=" + encrypted +
                '}';
    }
}
