package edu.episen.si.ing1.pds.backend.server.workspace;

import edu.episen.si.ing1.pds.backend.server.network.exchange.nio.Receiver;
import edu.episen.si.ing1.pds.backend.server.network.exchange.nio.Sender;
import edu.episen.si.ing1.pds.backend.server.network.exchange.nio.SocketExchange;
import edu.episen.si.ing1.pds.backend.server.network.exchange.socket.SocketHandler;
import edu.episen.si.ing1.pds.backend.server.network.exchange.socket.SocketParams;
import edu.episen.si.ing1.pds.backend.server.test.models.Companies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TestHandler implements SocketHandler {
    private final Logger logger = LoggerFactory.getLogger(TestHandler.class.getName());

    @Override
    public void handle(SocketExchange exchange) throws Exception {
        Receiver reader = exchange.getReceiver();
        Sender writer = exchange.getSender();

        logger.info("Here in Handler");

        String msg = reader.nextLine();
        if(msg != null) {
            Companies user = new Companies();
            List<Companies> list = user.all();
            writer.println("hashcode: " + SocketParams.getConnection().hashCode());
        }
        System.out.println(exchange);
    }
}
