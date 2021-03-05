package edu.episen.si.ing1.pds.backend.serveur.test.persistence;

import java.util.List;

public interface Repository {
    String read(int id);

    boolean update(int id, Object[] values);

    boolean create(String[] values);

    boolean delete(int id);

    List readAll();
}
