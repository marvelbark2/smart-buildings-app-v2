package edu.episen.si.ing1.pds.backend.server.network.exchange.routes;

import java.util.*;
import java.util.stream.Collectors;

public class Route {
    private final String route;
    private final List<String> arcRoute = new LinkedList<>();
    private final Object callback;
    private final boolean containParams;
    private final SocketMethod method;
    private final Map<String, String> param = new HashMap<>();
    private final Map<String, String> query = new HashMap<>();

    public Route(String route, Object callback, SocketMethod method) {
        this.route = route;
        this.callback = callback;
        this.containParams = route.contains("/:");
        this.method = method;
        this.arcRoute.addAll(Arrays.stream(route.split("/")).collect(Collectors.toList()));
    }

    public boolean isMe(String path) {
        if(route.equals(path)) {
            param.clear();
            return true;
        } else {
            if(containParams) {
                if(path.contains("?")) {
                    System.out.println(path);
                    String[] pQuery = path.split("\\?");
                    path = pQuery[0];
                    String queryURL = pQuery [1];
                    if(queryURL.contains("&")) {
                        String[] args = queryURL.split("&");
                        for (String arg: args) {
                            String[] argSplit = arg.split("=");
                            query.put(argSplit[0], argSplit[1]);
                        }
                    } else {
                        String[] arg = queryURL.split("=");
                        query.put(arg[0], arg[1]);
                    }
                }
                String[] pathSplit = path.split("/");
                if(pathSplit.length == arcRoute.size()) {
                    for (int i = 0; i < pathSplit.length; i++) {
                        String r = arcRoute.get(i);
                        String p = pathSplit[i];
                        if(!r.equals(p) && !r.contains(":")){
                            return false;
                        } else if(r.contains(":")){
                            param.put(r.substring(1), p);
                        }
                    }
                    return true;
                }
            } else {
                param.clear();
                query.clear();
            }
            return false;
        }
    }

    public Object getCallback() {
        if(callback instanceof CallBack) {
            CallBack cb = (CallBack) callback;
            if(containParams) {
                return cb.callBack(cb.callBack(param.values().toArray()));
            } else {
                return cb.callBack();
            }
        }
        return callback;
    }

    public Map<String, String> getParam() {
        return param;
    }

    public Map<String, String> getQuery() {
        return query;
    }

    public SocketMethod getMethod() {
        return method;
    }
}
