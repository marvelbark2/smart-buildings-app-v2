package edu.episen.si.ing1.pds.backend.server.workspace.shared;

import java.util.List;
import java.util.Optional;

public interface Services <Req, Res> {
    List<Res> findAll();
    Optional<Res> findById(Long id);
    Boolean add(Req request);
    Boolean delete(Req request);
}
