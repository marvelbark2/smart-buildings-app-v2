package edu.episen.si.ing1.pds.backend.server.network;

import edu.episen.si.ing1.pds.backend.server.network.config.SocketConfig;
import edu.episen.si.ing1.pds.backend.server.db.pool.DataSource;
import edu.episen.si.ing1.pds.backend.server.db.pool.PoolFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private Logger logger = LoggerFactory.getLogger(Server.class.getName());

    private final DataSource ds;
    private int nbConnection;
    private ExecutorService executor = Executors.newCachedThreadPool();
    private ServerSocket serverSocket;

    public Server(int nPool) {
    	ds = new DataSource(nPool, 100);
    }

    public void serve() {
        final int port = SocketConfig.Instance.PORT;
        logger.info("Server TCP Started on {}", port);
        try {
            serverSocket = new ServerSocket(port);
            while(true) {
                Socket socket = serverSocket.accept();
                ++nbConnection;

                Conversation conversation = new Conversation(socket, nbConnection);

                ds.returnable(PoolFactory.Instance.isNotReturnable());

                Connection connection = ds.getConnectionPool().getConnection();

                if(ds.poolSize() == 0) {
                    logger.info("Pool is Empty !");
                }
                conversation.setConnection(connection);

                executor.execute(conversation);
                ds.release(null);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            executor.shutdownNow();
            try {
                serverSocket.close();
            } catch (IOException ioException) {
                logger.info(ioException.getMessage());
            }
        }

    }

    private void broadcast() {

    }
}
