package edu.episen.si.ing1.pds.backend.server.network.exchange.routes;

import com.fasterxml.jackson.databind.JsonNode;

public interface PostCallBack {
    default Object callBack(JsonNode data)  {return null; }
    default Object callBack(JsonNode data, Object... t) { return null; }
}
