package edu.episen.si.ing1.pds.backend.serveur.db;

public enum PoolFactory {
    Instance;

    PoolFactory() {}

    Pool newPool(int n) {
        return new ConnectionPoolManager(n);
    }


}
