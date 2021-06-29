package edu.episen.si.ing1.pds.backend.server.network.exchange.handler;

import edu.episen.si.ing1.pds.backend.server.network.exchange.nio.Sender;
import edu.episen.si.ing1.pds.backend.server.network.exchange.nio.SocketExchange;
import edu.episen.si.ing1.pds.backend.server.network.exchange.routes.Route;
import edu.episen.si.ing1.pds.backend.server.network.exchange.socket.SocketHandler;

public class BaseHandler implements SocketHandler {

    @Override
    public void handle(SocketExchange exchange) throws Exception {
        Sender sender = exchange.getSender();
        Object callback = exchange.getRequest().getCallBack();
        sender.println(callback.toString());
    }
}
