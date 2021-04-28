package edu.episen.si.ing1.pds.client.network;

import edu.episen.si.ing1.pds.client.utils.Utils;

public class Request {
    private String requestId = Utils.generateStringId(25);
    private String event;
    private Object data;
    private Integer companyId = Utils.getCompanyId();

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

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
