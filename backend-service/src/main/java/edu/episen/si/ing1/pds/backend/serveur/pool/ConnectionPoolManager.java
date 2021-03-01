package edu.episen.si.ing1.pds.backend.serveur.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.*;

public class ConnectionPoolManager extends AbstractPool implements BlockingPool {

    private int nPool;
    private BlockingQueue<Connection> mountedConnection;
    private ExecutorService executor = Executors.newCachedThreadPool();
    private volatile boolean shutdownPool;

    public ConnectionPoolManager(int nPool) {
        this.nPool = nPool;
        mountedConnection = new LinkedBlockingQueue<>(nPool);
        init();
        shutdownPool = false;
    }

    private void init() {
        for (int i = 0; i < nPool; i++) {
            Connection connection = connectionFactory();
            mountedConnection.add(connection);
        }
    }

    private Connection connectionFactory() {
        Connection connection = null;
        PropertiesReader props = PropertiesReader.Instance;
        String url = props.HOST;
        String user = props.USER;
        String pass = props.PASS;
        try {
            connection = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    @Override
    protected void handleInvalidReturn(Connection con) {
        throw new IllegalStateException("Error! Cannot put this Connection: " + con.toString() + " to the pool");
    }

    @Override
    protected void returnToPool(Connection connection) {
        if (isValid(connection))
            executor.submit(new ObjectReturner(mountedConnection, connection));
    }

    @Override
    protected boolean isValid(Connection connection) {
        boolean status = false;
        try {
            status = (!(connection.equals(null) || connection.isClosed()));
        } catch (SQLException throwables) {
            Thread.currentThread().interrupt();
        }
        return status;
    }

    @Override
    public Connection getConnection(long time, TimeUnit unit) throws InterruptedException {
        if (!shutdownPool) {
            Connection connection = null;
            try {
                connection = mountedConnection.poll(time, unit);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
            return connection;
        } else {
            throw new IllegalStateException("Pool is shutdown");
        }
    }

    @Override
    public Connection getConnection() {
        if (!shutdownPool) {
            Connection connection = null;
            try {
                connection = mountedConnection.take();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
            return connection;
        } else {
            throw new IllegalStateException("Pool is shutdown");
        }
    }

    @Override
    public void shutdown() {
        shutdownPool = true;
        closeConnections();
        executor.shutdownNow();
    }

    private void closeConnections() {
        mountedConnection.stream()
                .forEach(connection -> {
                    try {
                        connection.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
    }

    private class ObjectReturner implements Runnable {
        private BlockingQueue<Connection> queue;
        private Connection connection;

        public ObjectReturner(BlockingQueue<Connection> queue, Connection connection) {
            this.queue = queue;
            this.connection = connection;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    queue.put(connection);
                    break;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

            }
        }
    }
}
