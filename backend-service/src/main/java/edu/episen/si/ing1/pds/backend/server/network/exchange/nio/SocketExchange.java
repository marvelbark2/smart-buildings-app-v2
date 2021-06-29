package edu.episen.si.ing1.pds.backend.server.network.exchange.nio;

import edu.episen.si.ing1.pds.backend.server.network.exchange.models.RequestSocket;
import edu.episen.si.ing1.pds.backend.server.network.exchange.nio.Receiver;
import edu.episen.si.ing1.pds.backend.server.network.exchange.nio.Sender;

public class SocketExchange {
    private final RequestSocket request;
    private final Sender sender;
    private boolean closed;

    public SocketExchange(RequestSocket receiver, Sender sender) {
        this.request = receiver;
        this.sender = sender;
    }

    public RequestSocket getRequest() {
        return request;
    }

    public Sender getSender() {
        return sender;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    @Override
    public String toString() {
        return "SocketExchange{" +
                "receiver=" + request +
                ", sender=" + sender +
                ", closed=" + closed +
                '}';
    }
}
