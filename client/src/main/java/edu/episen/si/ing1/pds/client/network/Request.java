package edu.episen.si.ing1.pds.client.network;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

public class Request {
    private String event;
    private Object data;

    public String getEvent() {
        return event;
    }

    public Object getData() {
        return data;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
