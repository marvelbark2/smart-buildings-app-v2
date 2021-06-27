package edu.episen.si.ing1.pds.backend.server.network.exchange.routes;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public enum RouteService {
    INSTANCE;
    private List<Route> routes = new ArrayList<>();

    public void get(String route, CallBack callBack) {
        routes.add(new Route(route, callBack,SocketMethod.GET));
    }

    public void get(String route, Class<?> clazz, String callback) {
        try {
            Method method;
            if(route.contains("{")) {
                method = clazz.getDeclaredMethod(callback, Object.class);
            } else {
                method = clazz.getDeclaredMethod(callback, null);
            }
            routes.add(new Route(route, method, SocketMethod.GET));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public Route findRoute(String path) {
        return routes.stream().filter(route -> route.isMe(path)).findFirst().orElse(null);
    }
}
