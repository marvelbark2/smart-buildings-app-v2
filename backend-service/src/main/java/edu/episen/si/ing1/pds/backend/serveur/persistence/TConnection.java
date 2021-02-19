package edu.episen.si.ing1.pds.backend.serveur.persistence;

import java.sql.Connection;
import java.sql.SQLException;

public class TConnection extends Thread {
    private PoolConnectionImpl poolConnection;
    private Connection connection;
    public TConnection(PoolConnectionImpl poolConnection) {
        this.poolConnection = poolConnection;
    }

    @Override
    public void run() {
            synchronized (poolConnection) {
                if(!poolConnection.isConnectionValid()) {
                    try {
                        poolConnection.wait();
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        this.connection = poolConnection.getConnection();
                        poolConnection.notifyAll();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
    }

    public Connection getConnection() {
        return connection;
    }
}
