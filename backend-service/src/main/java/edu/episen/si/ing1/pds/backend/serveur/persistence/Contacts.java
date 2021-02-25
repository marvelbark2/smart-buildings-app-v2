package edu.episen.si.ing1.pds.backend.serveur.persistence;

import edu.episen.si.ing1.pds.backend.serveur.db.DataSource;

import java.sql.*;
import java.util.Properties;

public class Contacts implements Repository {
    private DataSource pool;
    private Properties properties;

    public Contacts(DataSource pool) throws Exception {
        this.pool = pool;
        properties = pool.getProperties();
    }

    public String read(int id) {
        Connection connection = pool.getConnection();
        String result = "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("SQL.READ"));
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result = rs.getString(1);
            }
            pool.release(connection);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        } finally {
            pool.release(connection);
        }
        return result;
    }

    public boolean update(int id, String[] values) {
        boolean result = false;
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("SQL.UPDATE"));
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setString(i + 1, values[i]);
            }
            preparedStatement.setInt(values.length + 1, id);
            result = preparedStatement.executeUpdate() > 0 ? true : false;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            pool.release(connection);
        }
        return result;
    }

    public boolean create(String[] values) {
        boolean result = false;
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("SQL.CREATE"));
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setString(i + 1, values[i]);
            }
            result = preparedStatement.executeUpdate() > 0 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.release(connection);
        }

        return result;
    }

    public boolean delete(int id) {
        boolean result = false;
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("SQL.DELETE"));
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeUpdate() > 0 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.release(connection);
        }
        return result;
    }
}
