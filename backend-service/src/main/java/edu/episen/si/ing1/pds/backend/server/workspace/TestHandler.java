package edu.episen.si.ing1.pds.backend.server.workspace;

import edu.episen.si.ing1.pds.backend.server.network.exchange.Receiver;
import edu.episen.si.ing1.pds.backend.server.network.exchange.Sender;
import edu.episen.si.ing1.pds.backend.server.network.exchange.SocketExchange;
import edu.episen.si.ing1.pds.backend.server.network.exchange.SocketHandler;
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
