package edu.episen.si.ing1.pds.backend.server.workspace.shared;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Services <Req, Res> {
    List<Res> findAll();
    Optional<Res> findById(Long id);
    Boolean add(Req request) throws SQLException;
    Boolean delete(Req request);
    void setCompanyId(int companyId);
}
