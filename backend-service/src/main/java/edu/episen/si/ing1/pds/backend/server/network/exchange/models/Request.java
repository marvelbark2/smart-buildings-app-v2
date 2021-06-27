package edu.episen.si.ing1.pds.backend.server.network.exchange.models;

import com.fasterxml.jackson.databind.JsonNode;

public class Request {
    private String requestId;
    private String event;
    private JsonNode data;
    private Integer companyId;

    public String getEvent() {
        return event;
    }

    public JsonNode getData() {
        return data;
    }

    public String getRequestId() {
        return requestId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    @Override
    public String toString() {
        return "Request{" +
                "companyId='" + companyId + '\'' +
                "requestId='" + requestId + '\'' +
                ", event='" + event + '\'' +
                ", data=" + data +
                '}';
    }
}
