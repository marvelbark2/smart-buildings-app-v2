package edu.episen.si.ing1.pds.backend.server.network.server;

import edu.episen.si.ing1.pds.backend.server.network.exchange.SocketHandler;
import edu.episen.si.ing1.pds.backend.server.pool.DataSource;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;

public final class ServerBuilder {
    protected int ds;
    protected String configVar;
    protected boolean encrypted;
    protected boolean cacheThreadPool = false;
    protected int nbThread = 0;
    protected boolean testMode;
    protected SocketHandler handler;

    protected ServerBuilder() {
    }

    public ServerBuilder setDs(int ds) {
        this.ds = ds;
        return this;
    }

    public ServerBuilder setConfigVar(String configVar) {
        this.configVar = configVar;
        return this;
    }

    public ServerBuilder setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
        return this;
    }
    public ServerBuilder thread(int nbThread) {
        this.nbThread = nbThread;
        return this;
    }

    public ServerBuilder thread(boolean cacheThreadPool) {
        this.cacheThreadPool = cacheThreadPool;
        return this;
    }
    public ServerBuilder testMode(boolean testMode) {
        this.testMode = testMode;
        return this;
    }

    public ServerBuilder setHandler(SocketHandler handler) {
        this.handler = handler;
        return this;
    }

    public Server serve() throws Exception {
        return new Server(this);
    }

    @Override
    public String toString() {
        return "ServerBuilder{" +
                "ds=" + ds +
                ", configVar='" + configVar + '\'' +
                ", encrypted='" + encrypted + '\'' +
                ", cacheThreadPool=" + cacheThreadPool +
                ", nbThread=" + nbThread +
                ", testMode=" + testMode +
                '}';
    }
}
