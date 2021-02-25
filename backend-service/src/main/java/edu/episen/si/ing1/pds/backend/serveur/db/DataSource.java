package edu.episen.si.ing1.pds.backend.serveur.db;

import java.sql.Connection;
import java.util.Properties;

public class DataSource {
    private Pool pool;

    public DataSource(int n) { pool = PoolFactory.Instance.newPool(n); }

    public Connection getConnection() {
        return pool.getConnection();
    }
    public void shutdownPool() { pool.shutdown(); }
    public void release(Connection connection){ pool.release(connection); }
    public Properties getProperties(){ return PropertiesReader.Instance.getProperties(); }
}
