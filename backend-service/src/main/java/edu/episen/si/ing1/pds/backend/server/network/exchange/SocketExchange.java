package edu.episen.si.ing1.pds.backend.server.network.exchange;

public class SocketExchange {
    private final Receiver receiver;
    private final Sender sender;

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
}
