package edu.episen.si.ing1.pds.backend.serveur;

import edu.episen.si.ing1.pds.backend.serveur.pool.DataSource;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TestPool {
    private DataSource ds = new DataSource(25);

    public List<CompletableFuture<Connection>> getTest() {
        List<CompletableFuture<Connection>> futures = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            futures.add(getConnection());
        }
        return futures;
    }
    private CompletableFuture<Connection> getConnection() {
        return CompletableFuture.supplyAsync(() -> ds.getConnection());
    }
}
