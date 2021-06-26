package edu.episen.si.ing1.pds.backend.server.network.server;

import edu.episen.si.ing1.pds.backend.server.network.exchange.Receiver;
import edu.episen.si.ing1.pds.backend.server.network.exchange.Sender;
import edu.episen.si.ing1.pds.backend.server.network.exchange.SocketHandler;
import edu.episen.si.ing1.pds.backend.server.network.exchange.SocketParams;
import edu.episen.si.ing1.pds.backend.server.pool.ConnectionPool;
import edu.episen.si.ing1.pds.backend.server.pool.DataSource;
import edu.episen.si.ing1.pds.backend.server.utils.Properties;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final Logger logger = LoggerFactory.getLogger(Server.class.getName());

    private Server() { }
    public Server(ServerBuilder builder) throws Exception {
        Properties.testMode = builder.testMode;
        Utils.configVar = builder.configVar;
        if(builder.cacheThreadPool) {
            Properties.executor = Executors.newCachedThreadPool();
        } else if(builder.nbThread != 0){
            Properties.executor = Executors.newFixedThreadPool(builder.nbThread);
        } else {
            throw new IllegalAccessException("Bad Configuration to handle Server Socket thread");
        }
        if(builder.ds != 0) {
            DataSource ds = new DataSource(builder.ds, 100);
            SocketServer server = new SocketServer(builder.encrypted);
            server.setDataSource(ds);
            ExecutorService service = Properties.executor;
            service.execute(new Executable(server, builder.handler));
            logger.info("Pool connections: {}", ds.poolSize());
        }
        logger.info("Builder {}", builder);


    }

    public static ServerBuilder init() {
        return new ServerBuilder();
    }

    private class Executable implements Runnable {
        private final SocketServer server;
        private final SocketHandler handler;

        public Executable(SocketServer server, SocketHandler handler){
            this.server = server;
            this.handler = handler;
        }

        @Override
        public void run() {
            try {
                server.startServer(handler);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
