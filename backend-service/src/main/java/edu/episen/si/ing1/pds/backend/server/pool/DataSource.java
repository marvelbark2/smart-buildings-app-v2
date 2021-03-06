package edu.episen.si.ing1.pds.backend.server.pool;

import java.sql.Connection;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class DataSource {
    private BlockingPool pool;
    private long time;

    public DataSource(int n, long time) {

        pool = PoolFactory.Instance.newPool(n);
        this.time = time;
    }

    public Connection getConnection() {
        return pool.getConnection(time, TimeUnit.MILLISECONDS);
    }

    public void shutdownPool() {
        pool.shutdown();
    }

    public void release(Connection connection) {
        pool.release(connection);
    }

    public int poolSize() {
        return pool.poolSize();
    }

    public Properties getProperties() {
        return PropertiesReader.Instance.getProperties();
    }
}
