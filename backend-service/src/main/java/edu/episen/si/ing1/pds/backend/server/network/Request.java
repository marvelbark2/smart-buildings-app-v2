package edu.episen.si.ing1.pds.backend.server.network;

import com.fasterxml.jackson.databind.JsonNode;

public class Request {
    private String event;
    private JsonNode data;

    public String getEvent() {
        return event;
    }

    public JsonNode getData() {
        return data;
    }
}
