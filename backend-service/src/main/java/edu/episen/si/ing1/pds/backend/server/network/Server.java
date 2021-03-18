package edu.episen.si.ing1.pds.backend.server.network;

import edu.episen.si.ing1.pds.backend.server.pool.DataSource;
import edu.episen.si.ing1.pds.backend.server.test.persistence.Contacts;
import edu.episen.si.ing1.pds.backend.server.test.persistence.Repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Server extends Thread {
    private int port = SocketConfig.Instance.PORT;
    private Logger logger = LoggerFactory.getLogger(Server.class.getName());
    private DataSource ds;
    private int nbConnection;
    private static List<Conversation> clients = new ArrayList<>();
    private ExecutorService executor = Executors.newCachedThreadPool();
    private ServerSocket serverSocket;
    
    public Server(int nPool) {
    	ds = new DataSource(nPool);
    }

    public static List<Conversation> getClients() {
        return clients;
    }

    @Override
    public void run() {
        logger.info("Server TCP Started on {}", port);
        try {
            serverSocket = new ServerSocket(port);
            while(true) {
                Socket socket = serverSocket.accept();
                ++nbConnection;
                Conversation conversation = new Conversation(socket, nbConnection);
                Repository contact = new Contacts(ds);
                conversation.setRepository(contact);
                clients.add(conversation);
                executor.submit(conversation);
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
