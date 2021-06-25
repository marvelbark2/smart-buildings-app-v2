package edu.episen.si.ing1.pds.backend.server.workspace;

import edu.episen.si.ing1.pds.backend.server.network.exchange.Receiver;
import edu.episen.si.ing1.pds.backend.server.network.exchange.Sender;
import edu.episen.si.ing1.pds.backend.server.network.exchange.SocketExchange;
import edu.episen.si.ing1.pds.backend.server.network.exchange.SocketHandler;

public class TestHandler implements SocketHandler {
    @Override
    public void handle(SocketExchange exchange) throws Exception {
        Receiver reader = exchange.getReceiver();
        Sender writer = exchange.getSender();

        String msg = reader.nextLine();
        System.out.println("msg received is " + msg);

        writer.println("received :" + msg);
    }
}
