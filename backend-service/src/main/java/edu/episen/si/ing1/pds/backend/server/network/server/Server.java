package edu.episen.si.ing1.pds.backend.server.network.server;

import edu.episen.si.ing1.pds.backend.server.pool.DataSource;
import edu.episen.si.ing1.pds.backend.server.utils.Properties;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            for (int i = 0; i < 6; i++) {
                Connection connection = ds.getConnectionPool().getConnection();
                String sql = "SELECT * FROM cards LIMIT 2";
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql);
                while (rs.next()) {
                    logger.info("first data {}", rs.getInt("cardid"));
                }
            }
            logger.info("Pool connections: {}", ds.poolSize());
        }
        logger.info("Builder {}", builder);


    }

    private void serve() {

    }

    public static ServerBuilder init() {
        return new ServerBuilder();
    }
}
