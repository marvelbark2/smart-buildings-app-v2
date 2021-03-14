package edu.episen.si.ing1.pds.backend.server.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends Thread {
    private int port = SocketConfig.Instance.PORT;
    private int nbConnection;
    private Logger logger = LoggerFactory.getLogger(Server.class.getName());
    private List<Conversation> clients = new ArrayList<>();
    private ExecutorService executor = Executors.newCachedThreadPool();
    private ServerSocket serverSocket;
    @Override
    public void run() {
        logger.info("Server TCP Started on {}", port);
        try {
            serverSocket = new ServerSocket(port);
            while(true) {
                Socket socket = serverSocket.accept();
                ++nbConnection;
                Conversation conversation = new Conversation(socket, nbConnection);
//                executor.submit(conversation);
                conversation.start();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            executor.shutdownNow();
            try {
                serverSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }
}
