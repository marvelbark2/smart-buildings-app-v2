package edu.episen.si.ing1.pds.backend.serveur.persistence;

import java.sql.Connection;
import java.util.Properties;

public class ConnectionSingleton {

    private static ConnectionSingleton instance;
    private Connection connection;
    private Properties properties;

    public Connection getConnection() {
        return connection;
    }

    public Properties getProperties() {
        return properties;
    }

    private ConnectionSingleton(int n) {
        ConnectionPool pool = new IConnectionPool(n);
        try {
            connection = pool.getConnection();
            properties = pool.getProperties();
        } catch (Exception e) {e.printStackTrace();}

    }
    public static ConnectionSingleton getInstance(int n) {

        if (instance == null) {
            instance = new ConnectionSingleton(n);
        }
        return instance;
    }
}
