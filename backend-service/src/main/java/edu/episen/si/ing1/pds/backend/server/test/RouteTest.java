package edu.episen.si.ing1.pds.backend.server.test;

import edu.episen.si.ing1.pds.backend.server.network.exchange.routes.CallBack;
import edu.episen.si.ing1.pds.backend.server.network.exchange.routes.Route;
import edu.episen.si.ing1.pds.backend.server.network.exchange.routes.RouteService;

public class RouteTest {
    public static void main(String[] args) {
        RouteService.INSTANCE.get("/tot/me", new CallBack() {
            @Override
            public Object callBack() {
                return "toto";
            }
        });
        RouteService.INSTANCE.get("/yo/me", new CallBack() {
            @Override
            public Object callBack() {
                return "toto";
            }
        });
        RouteService.INSTANCE.get("/mon/me", new CallBack() {
            @Override
            public Object callBack() {
                return "toto";
            }
        });
        RouteService.INSTANCE.get("/tot/:me", new CallBack() {
            @Override
            public Object callBack(Object... t) {
                return  t[0];
            }
        });
        RouteService.INSTANCE.get("/bre/me/:mo", new CallBack() {
            @Override
            public Object callBack(Object... t) {
                return t[0];
            }
        });

        Route r = RouteService.INSTANCE.findRoute("/mon/me");
        Route r2 = RouteService.INSTANCE.findRoute("/tot/234");
        Route r3 = RouteService.INSTANCE.findRoute("/bre/me/3442");

        System.out.println(r.getCallback());
        System.out.println(r2.getParam());
        System.out.println(r3.getCallback());

        Object me = r2.getCallback();
        System.out.println(me);
    }
}
