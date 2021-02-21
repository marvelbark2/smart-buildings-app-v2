package edu.episen.si.ing1.pds.backend.serveur.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public interface ConnectionPool {
    public Connection getConnection() throws SQLException;
    public Boolean close(Connection c) throws SQLException;
    public Boolean shutdown();
    public Properties getProperties();
}
