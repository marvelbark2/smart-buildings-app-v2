package edu.episen.si.ing1.pds.backend.serveur.persistence;

public interface Repository {
    String read(int id);
    boolean update(int id, String[] values);
    boolean create(String[] values);
    boolean delete(int id);
}
