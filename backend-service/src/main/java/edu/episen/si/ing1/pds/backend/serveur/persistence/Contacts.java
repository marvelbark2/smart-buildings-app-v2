package edu.episen.si.ing1.pds.backend.serveur.persistence;

import edu.episen.si.ing1.pds.backend.serveur.pool.DataSource;

import java.sql.*;
import java.util.Properties;

public class Contacts implements Repository {
    private DataSource dataSource;
    private Properties properties;

    public Contacts(DataSource dataSource) throws Exception {
        this.dataSource = dataSource;
        properties = dataSource.getProperties();
    }

    public String read(int id) {
        Connection connection = dataSource.getConnection();
        System.out.println(connection.hashCode());
        String result = "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("SQL.READ"));
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result = rs.getString(1);
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        } finally {
            dataSource.release(connection);
        }
        return result;
    }

    public boolean update(int id, String[] values) {
        boolean result = false;
        Connection connection = dataSource.getConnection();
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
            dataSource.release(connection);
        }
        return result;
    }

    public boolean create(String[] values) {
        boolean result = false;
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("SQL.CREATE"));
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setString(i + 1, values[i]);
            }
            result = preparedStatement.executeUpdate() > 0 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dataSource.release(connection);
        }

        return result;
    }

    public boolean delete(int id) {
        boolean result = false;
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("SQL.DELETE"));
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeUpdate() > 0 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dataSource.release(connection);
        }
        return result;
    }
}
