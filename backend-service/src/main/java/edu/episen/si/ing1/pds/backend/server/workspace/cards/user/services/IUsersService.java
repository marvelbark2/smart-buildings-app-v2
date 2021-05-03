package edu.episen.si.ing1.pds.backend.server.workspace.cards.user.services;

import com.fasterxml.jackson.databind.JsonNode;
import edu.episen.si.ing1.pds.backend.server.workspace.shared.Services;

import java.util.Map;

public interface IUsersService<UserReq,UserRes> extends Services<UserReq,UserRes> {
    JsonNode findUser(UserReq user);
}
