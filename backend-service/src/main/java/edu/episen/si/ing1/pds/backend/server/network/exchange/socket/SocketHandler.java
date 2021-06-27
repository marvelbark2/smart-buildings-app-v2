package edu.episen.si.ing1.pds.backend.server.network.exchange.socket;

import edu.episen.si.ing1.pds.backend.server.network.exchange.nio.SocketExchange;

public interface SocketHandler {
    void handle(SocketExchange exchange) throws Exception;
}
