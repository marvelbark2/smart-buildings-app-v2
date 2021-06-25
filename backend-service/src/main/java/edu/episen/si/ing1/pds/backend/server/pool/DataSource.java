package edu.episen.si.ing1.pds.backend.server.pool;

import java.sql.Connection;
import java.util.concurrent.TimeUnit;

public class DataSource {
    private BlockingPool pool;
    private long time;

    public DataSource(int n, int max, long time) {
        pool = PoolFactory.Instance.newPool(n, max);
        this.time = time;
    }
    public DataSource(int n, int max) {
        pool = PoolFactory.Instance.newPool(n, max);
        this.time = 1_000;
    }

    public ConnectionPool getConnectionPool() {
        return pool.getConnection(time, TimeUnit.MILLISECONDS);
    }

    public void shutdownPool() {
        pool.shutdown();
    }

    public void release(ConnectionPool connection) {
        pool.release(connection);
    }

    public int poolSize() {
        return pool.poolSize();
    }

    public void returnable(Boolean v) { pool.isReturnedTo(v); }
}
