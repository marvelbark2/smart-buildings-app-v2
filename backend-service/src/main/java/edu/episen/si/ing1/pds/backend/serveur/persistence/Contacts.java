package edu.episen.si.ing1.pds.backend.serveur.persistence;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class Contacts {
    private Connection connection;
    Properties properties;
    public Contacts(int n) throws Exception {
        PoolConnectionImpl pool = new PoolConnectionImpl(n);
        properties = new Properties();
        InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.dev.properties");
        properties.load(inStream);
        Class.forName(properties.getProperty("DB.ClassDriver"));
        String url = properties.getProperty("DB.HOST");
        String user = properties.getProperty("DB.USER");
        String password = properties.getProperty("DB.PASS");
        PoolConnectionImpl poolConnection = pool.create(url, user, password);

        TConnection tConnection = new TConnection(poolConnection);
        tConnection.start();
        this.connection = poolConnection.getConnection();
    }

    public String read() throws Exception {
        Statement statement = connection.createStatement();
        String query = properties.getProperty("SQL.READ");
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            return rs.getString(1);
        }
        return "";
    }
}
