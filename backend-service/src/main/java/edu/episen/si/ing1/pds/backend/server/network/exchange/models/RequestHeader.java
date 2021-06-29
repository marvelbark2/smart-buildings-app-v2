package edu.episen.si.ing1.pds.backend.server.network.exchange.models;

import edu.episen.si.ing1.pds.backend.server.network.exchange.routes.Route;
import edu.episen.si.ing1.pds.backend.server.network.exchange.routes.SocketMethod;

import java.util.Map;

public class RequestHeader {
    private String path;
    private Route route;

    public RequestHeader() {
    }

    public RequestHeader(String path, Route route) {
        this.path = path;
        this.route = route;
    }

    public String getPath() {
        return path;
    }


    public Map<String, String> getParam() {
        return route.getParam();
    }


    public Map<String, String> getQuery() {
        return route.getQuery();
    }


    public SocketMethod getMethod() {
        return route.getMethod();
    }

    @Override
    public String toString() {
        return "RequestHeader{" +
                "path='" + path + '\'' +
                ", route=" + route +
                '}';
    }
}
