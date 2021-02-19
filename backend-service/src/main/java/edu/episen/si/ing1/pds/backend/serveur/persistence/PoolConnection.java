package edu.episen.si.ing1.pds.backend.serveur.persistence;

import java.sql.Connection;
import java.sql.SQLException;

public interface PoolConnection {
    Connection getConnection() throws SQLException;
    boolean closeConnection(Connection connection);
    String getHost();
    String getUser();
    String getPassword();
}
