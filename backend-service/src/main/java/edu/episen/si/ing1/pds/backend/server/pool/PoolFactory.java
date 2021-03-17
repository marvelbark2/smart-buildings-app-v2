package edu.episen.si.ing1.pds.backend.server.pool;

public enum PoolFactory {
    Instance;

    BlockingPool pool;
    PoolFactory() {
        pool = new ConnectionPoolManager();
    }

    BlockingPool newPool(int n) {
        pool.init(n);
        return pool;
    }


}
