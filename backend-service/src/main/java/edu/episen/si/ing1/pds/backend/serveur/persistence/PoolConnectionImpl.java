package edu.episen.si.ing1.pds.backend.serveur.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PoolConnectionImpl implements PoolConnection {
    private static final int MAX_TIMEOUT = 2_500;
    private String url;
    private String user;
    private String password;
    private List<Connection> session;
    private List<Connection> usedSession = new ArrayList<>();
    private static int connectionNumber = 10;

    protected PoolConnectionImpl(String url, String user, String password, List<Connection> session) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.session = session;
    }

    public PoolConnectionImpl(int connectionNumber) throws Exception {
        this.connectionNumber = connectionNumber;
    }

    public PoolConnectionImpl create(
            String url, String user,
            String password) throws SQLException {

        List<Connection> pool = new ArrayList<>(this.connectionNumber);

        for (int i = 0; i < this.connectionNumber; i++) {
            pool.add(createConnection(url, user, password));
        }
        return new PoolConnectionImpl(url, user, password, pool);
    }

    private static Connection createConnection(
            String url, String user, String password)
            throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
    @Override
    public Connection getConnection() throws SQLException {
        if (session.isEmpty()) {
            if (usedSession.size() < connectionNumber) {
                session.add(createConnection(url, user, password));
            } else {
                throw new RuntimeException(
                        "Maximum pool has been Reached!");
            }
        }

        Connection connection = session
                .remove(session.size() - 1);

        if(!connection.isValid(MAX_TIMEOUT)){
            connection = createConnection(url, user, password);
        }

        usedSession.add(connection);
        return connection;
    }
    @Override
    public boolean closeConnection(Connection connection) {
        session.add(connection);
        return session.remove(connection);
    }

    public boolean isConnectionValid() {
        return this.session.size() > this.usedSession.size();
    }

    @Override
    public String getHost() {
        return url;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
