package edu.episen.si.ing1.pds.backend.serveur.test.persistence;

import edu.episen.si.ing1.pds.backend.serveur.pool.DataSource;

import java.sql.*;
import java.util.*;

public class Contacts implements Repository {
    private DataSource dataSource;
    private Properties properties;

    public Contacts(DataSource dataSource) {
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

    public boolean update(int id, Object[] values) {
        boolean result = false;
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("SQL.UPDATE"));
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setString(i + 1, String.valueOf(values[i]));
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

    public List readAll() {
        Connection connection = dataSource.getConnection();
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = properties.getProperty("SQL.READALL");
            ResultSet resultSet = statement.executeQuery(query);
            int cols = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                for(int i=1; i<=cols; ++i){
                    row.put(resultSet.getMetaData().getColumnName(i),resultSet.getObject(i));
                }
                result.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dataSource.release(connection);
        }
        return result;
    }
}
