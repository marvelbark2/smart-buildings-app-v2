package edu.episen.si.ing1.pds.backend.serveur.pool;

public enum PoolFactory {
    Instance;

    PoolFactory() {
    }

    Pool newPool(int n) {
        return new ConnectionPoolManager(n);
    }


}
