package edu.episen.si.ing1.pds.backend.server.network.exchange.models;

import com.fasterxml.jackson.databind.JsonNode;

public class RequestSocket {
    private String requestId;
    private RequestHeader header;
    private Object callBack;
    private JsonNode body;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public RequestHeader getHeader() {
        return header;
    }

    public void setHeader(RequestHeader header) {
        this.header = header;
    }

    public JsonNode getBody() {
        return body;
    }

    public void setBody(JsonNode body) {
        this.body = body;
    }

    public Object getCallBack() {
        return callBack;
    }

    public void setCallBack(Object callBack) {
        this.callBack = callBack;
    }

    @Override
    public String toString() {
        return "RequestSocket{" +
                "requestId='" + requestId + '\'' +
                ", header=" + header +
                ", body=" + body +
                '}';
    }
}
