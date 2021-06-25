package edu.episen.si.ing1.pds.backend.server.pool;

import java.sql.Connection;

public abstract class AbstractPool implements Pool {

    @Override
    public void release(ConnectionPool connection) {
        if (isValid(connection)) {
            returnToPool(connection);
        } else {
            handleInvalidReturn(connection);
        }
    }


    protected abstract void handleInvalidReturn(ConnectionPool t);

    protected abstract void returnToPool(ConnectionPool t);

    protected abstract boolean isValid(ConnectionPool t);
}
