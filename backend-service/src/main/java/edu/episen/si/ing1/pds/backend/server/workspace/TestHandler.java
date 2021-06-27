package edu.episen.si.ing1.pds.backend.server.workspace;

import edu.episen.si.ing1.pds.backend.server.network.exchange.nio.Receiver;
import edu.episen.si.ing1.pds.backend.server.network.exchange.nio.Sender;
import edu.episen.si.ing1.pds.backend.server.network.exchange.nio.SocketExchange;
import edu.episen.si.ing1.pds.backend.server.network.exchange.socket.SocketHandler;
import edu.episen.si.ing1.pds.backend.server.test.models.Companies;

import java.util.List;

public class TestHandler implements SocketHandler {
    @Override
    public void handle(SocketExchange exchange) throws Exception {
        Receiver reader = exchange.getReceiver();
        Sender writer = exchange.getSender();

        String msg = reader.nextLine();
        if(msg != null) {
            System.out.println("receiving: " + msg);
            Companies user = new Companies();
            List<Companies> list = user.all();
            System.out.println(list);
            writer.println("list: " + list);
        }
        System.out.println(exchange);
    }
}
