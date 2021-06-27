package edu.episen.si.ing1.pds.backend.server.network.exchange.nio;

import edu.episen.si.ing1.pds.backend.server.network.exchange.nio.Receiver;
import edu.episen.si.ing1.pds.backend.server.network.exchange.nio.Sender;

public class SocketExchange {
    private final Receiver receiver;
    private final Sender sender;
    private boolean closed;

    public SocketExchange(Receiver receiver, Sender sender) {
        this.receiver = receiver;
        this.sender = sender;
    }

    public Receiver getReceiver() {
        return receiver;
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
                "receiver=" + receiver +
                ", sender=" + sender +
                ", closed=" + closed +
                '}';
    }
}
