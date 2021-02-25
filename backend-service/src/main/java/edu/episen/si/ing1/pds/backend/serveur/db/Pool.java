package edu.episen.si.ing1.pds.backend.serveur.db;

import java.sql.Connection;

public interface Pool {
    Connection getConnection();
    void release(Connection connection);
    void shutdown();
}
