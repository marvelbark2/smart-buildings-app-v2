package edu.episen.si.ing1.pds.backend.server.network.exchange;

public interface SocketHandler {
    void handle(SocketExchange exchange) throws Exception;
}
