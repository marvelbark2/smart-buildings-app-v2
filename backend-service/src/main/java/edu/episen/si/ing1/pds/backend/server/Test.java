package edu.episen.si.ing1.pds.backend.server;

import edu.episen.si.ing1.pds.backend.server.pool.DataSource;

import java.sql.Connection;

public class Test {
    public static void main(String[] args) {
        DataSource ds = new DataSource(12, 1200);
        Connection connection = ds.getConnection();
        System.out.println(connection.hashCode());
    }
}
