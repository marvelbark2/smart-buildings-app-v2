package edu.episen.si.ing1.pds.backend.serveur.db;

import java.sql.Connection;
import java.util.concurrent.TimeUnit;

public interface BlockingPool extends Pool {
    Connection getConnection(long time, TimeUnit unit) throws InterruptedException;
}
