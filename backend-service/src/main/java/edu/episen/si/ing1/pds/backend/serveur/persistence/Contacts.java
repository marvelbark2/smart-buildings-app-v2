package edu.episen.si.ing1.pds.backend.serveur.persistence;

import java.sql.*;
import java.util.Properties;

public class Contacts {
    private ConnectionPool pool;
    private Connection connection;
    private Properties properties;

    public Contacts(int n) throws Exception {
        pool = new IConnectionPool(n);
        connection = pool.getConnection();
        properties = pool.getProperties();
    }

    public String read(int id) throws Exception {
        String result = "";
        PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("SQL.READ"));
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            result = rs.getString(1);
        }
        return result;
    }

    public boolean update(int id, String[] values) {
        boolean result = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("SQL.UPDATE"));
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setString(i+1, values[i]);
            }
            preparedStatement.setInt(values.length + 1, id);
            result = preparedStatement.executeUpdate() > 0 ? true : false;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public boolean create(String[] values) {
        boolean result = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("SQL.CREATE"));
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setString(i+1, values[i]);
            }
            result = preparedStatement.executeUpdate() > 0 ? true : false;
        } catch (Exception e) {e.printStackTrace();}

        return result;
    }

    public boolean delete(int id) {
        boolean result = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("SQL.DELETE"));
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeUpdate() > 0 ? true : false;
        } catch (Exception e) {e.printStackTrace();}
        return result;
    }

    public boolean closeConnection() throws Exception {
        return pool.checkout(connection);
    }
}
