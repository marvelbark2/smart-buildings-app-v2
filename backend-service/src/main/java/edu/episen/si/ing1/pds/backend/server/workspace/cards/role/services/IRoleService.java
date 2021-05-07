package edu.episen.si.ing1.pds.backend.server.workspace.cards.role.services;

import com.fasterxml.jackson.databind.node.ArrayNode;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.Services;

public interface IRoleService<Req, Res> extends Services<Req, Res> {
    ArrayNode accessList(Req request);
    Boolean updateAccessList(ArrayNode list, Req request);
}
