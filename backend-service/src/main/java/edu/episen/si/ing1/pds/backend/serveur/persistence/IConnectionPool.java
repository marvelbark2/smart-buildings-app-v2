package edu.episen.si.ing1.pds.backend.serveur.persistence;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

public class IConnectionPool implements ConnectionPool {
    private List<Connection> mountedConnetion;
    private List<Connection> usedConnection;
    private int nPool;
    private Properties properties;

    public IConnectionPool(int nPool) {

        this.nPool = nPool;
        mountedConnetion = new ArrayList<>();
        usedConnection = new ArrayList<>();
        properties = new Properties();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.dev.properties");
        try {
            properties.load(inputStream);
            Class.forName(properties.getProperty("DB.ClassDriver"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < nPool; i++) {
            try {
                mountedConnetion.add(connectionFactory());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection;
        if(! mountedConnetion.isEmpty()){
            connection = mountedConnetion.remove(mountedConnetion.size() - 1);
            usedConnection.add(connection);
        } else {
            connection = usedConnection.get(0);
            mountedConnetion.remove(connection);
        }

        return connection;
    }


    public Boolean close(Connection c) throws SQLException {
        checkout(c);
        c.close();
        return c.isClosed();
    }

    @Override
    public Boolean checkout(Connection c) {
        Boolean status = true;
        if(usedConnection.contains(c)) {
            usedConnection.remove(c);
        }
        if(mountedConnetion.contains(c)){
            mountedConnetion.remove(c);
        }
        return status;
    }
    @Override
    public Boolean shutdown() {
        AtomicReference<Boolean> status = new AtomicReference<>(true);
        usedConnection.stream()
                .forEach(connection -> {
                    try {
                        connection.close();
                    } catch (SQLException throwables) {
                        status.set(false);
                    }
                });
        usedConnection.clear();
        return status.get();
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    private Connection connectionFactory() throws SQLException{
        Connection connection = DriverManager.getConnection(properties.getProperty("DB.HOST"), properties.getProperty("DB.USER"), properties.getProperty("DB.PASS"));
        return connection;
    }
}
