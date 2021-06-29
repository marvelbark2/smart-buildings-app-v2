package edu.episen.si.ing1.pds.backend.server.db.pool;

import java.util.concurrent.TimeUnit;

public interface BlockingPool extends Pool {
    ConnectionPool getConnection(long time, TimeUnit unit);
    int poolSize();
}
