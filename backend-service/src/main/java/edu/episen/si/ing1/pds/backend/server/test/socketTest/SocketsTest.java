package edu.episen.si.ing1.pds.backend.server.test.socketTest;

import edu.episen.si.ing1.pds.backend.server.network.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketsTest {
    static final Logger logger = LoggerFactory.getLogger(SocketsTest.class.getName());
    public static void main(String[] args) {
        logger.info("TestSocket Started");
        Server serverSocket = new Server();
        serverSocket.start();
    }
}
