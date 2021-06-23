package edu.episen.si.ing1.pds.backend.server.workspace.cards;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Services <Req, Res> {
    List<Res> findAll();
    Optional<Res> findById(Integer id);
    Boolean add(Req request) throws SQLException;
    Boolean delete(Req request);
    Boolean update(Req request, Integer id);
    void setCompanyId(int companyId);
}
