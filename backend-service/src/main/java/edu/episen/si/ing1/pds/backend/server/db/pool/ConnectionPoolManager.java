package edu.episen.si.ing1.pds.backend.server.db.pool;

import edu.episen.si.ing1.pds.backend.server.db.pool.config.DBConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.*;

public class ConnectionPoolManager extends AbstractPool implements BlockingPool {
    private final Logger logger = LoggerFactory.getLogger(ConnectionPoolManager.class.getName());
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private BlockingQueue<ConnectionPool> mountedConnection;
    private volatile boolean shutdownPool;
    private boolean isReturned = true;
    private int maxConnection;
    private int currentConnection = 0;

    public ConnectionPoolManager() {
        shutdownPool = false;
    }

    public void init(int nPool, int maxConnection) {
        logger.info("Pool Init...ted");
        this.maxConnection = maxConnection;
        mountedConnection = new LinkedBlockingQueue<>(nPool);
        for (int i = 0; i < nPool; i++) {
            ConnectionPool connection = connectionFactory(false);
            mountedConnection.add(connection);
        }
        logger.info("Pool connections : {}", mountedConnection.size());
    }

    private ConnectionPool connectionFactory(Boolean tmp) {
        Connection connection = null;
        DBConfig config = DBConfig.Instance;
        String url = config.HOST;
        String user = config.USER;
        String pass = config.PASS;
        try {
            connection = DriverManager.getConnection(url, user, pass);
            ++currentConnection;
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            Thread.currentThread().interrupt();
        }

        return new ConnectionPool(connection, tmp);
    }

    @Override
    protected void handleInvalidReturn(ConnectionPool con) {
        throw new IllegalStateException("Error! Cannot put this Connection: " + con + " to the pool");
    }

    @Override
    protected void returnToPool(ConnectionPool connection) {
        if (isValid(connection))
            executor.submit(new ObjectReturner(mountedConnection, connection));
    }

    @Override
    protected boolean isValid(ConnectionPool connection) {
        try {
            if (connection == null || connection.isClosed())
                return false;
            else {
                Connection con = connection.getConnection();
                if(con == null) {
                    return  false;
                } else return !con.isClosed();
            }
        } catch (SQLException throwables) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public ConnectionPool getConnection(long time, TimeUnit unit) {
        if (!shutdownPool) {
            try {
                Future<ConnectionPool> future = executor.submit(() -> {
                    int i = 0;
                    while (true) {
                        if (mountedConnection.size() != 0 && maxConnection >= currentConnection) {
                            ConnectionPool connectionPool = mountedConnection.poll(time, unit);
                            if(connectionPool != null){
                                i = 0;
                                return getConnection(connectionPool);
                            } else {
                                return connectionFactory(true);
                            }
                        } else {
                            if(i > 0) {
                                return connectionFactory(true);
                            } else {
                                Thread.sleep(time);
                                i++;
                            }
                        }
                    }
                });
                while (true) {
                    if(future.isDone()) {
                         return future.get();
                    }
                }
            } catch (Exception ie) {
                logger.error(ie.getLocalizedMessage(), ie);
                Thread.currentThread().interrupt();
                return null;
            }

        } else {
            throw new IllegalStateException("Pool is shutdown");
        }
    }

    private ConnectionPool getConnection(ConnectionPool connectionPool) {
        if (!isValid(connectionPool)) {
            mountedConnection.remove(connectionPool);
            connectionPool = connectionFactory(false);
        }
        return connectionPool;
    }

    @Override
    public int poolSize() {
        return mountedConnection.size();
    }

    @Override
    public Connection getConnection() {
        if (!shutdownPool) {
            Connection connection = null;
            try {
                Future<ConnectionPool> future = executor.submit(() -> {
                    while (true) {
                        if (mountedConnection.size() != 0) {
                            ConnectionPool connectionPool = mountedConnection.take();
                            return getConnection(connectionPool);
                        } else {
                            mountedConnection.wait(1000);
                        }
                    }
                });
                while (true) {
                    if(future.isDone()) {
                        //TODO: Update it to be integrable in pool system
                        connection = future.get().getConnection();
                        break;
                    }
                }
            } catch (Exception ie) {
                Thread.currentThread().interrupt();
            }
            return connection;
        } else {
            throw new IllegalStateException("Pool is shutdown");
        }
    }

    @Override
    public void isReturnedTo(Boolean v) {
        this.isReturned = v;
    }

    @Override
    public void shutdown() {
        shutdownPool = true;
        executor.shutdownNow();
        closeConnections();
    }

    private void closeConnections() {
        mountedConnection
                .forEach(connection -> {
                    try {
                        connection.close();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                });
    }

    private class ObjectReturner implements Runnable {
        private final BlockingQueue<ConnectionPool> queue;
        private final ConnectionPool connection;

        ObjectReturner(BlockingQueue<ConnectionPool> queue, ConnectionPool connection) {
            this.queue = queue;
            this.connection = connection;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    if (isReturned && !connection.isTmp() && !connection.isClosed()) {
                        queue.put(connection);
                        logger.info("Connection returned to pool  {}", connection);
                    } else {
                        connection.close();
                        logger.info("Connection closed: {}", connection);
                    }
                    --currentConnection;
                    logger.info("Recalcul pool size {}, size fun : {}", mountedConnection.size(), queue.size());
                    break;
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }

            }
        }
    }
}
