package edu.episen.si.ing1.pds.backend.server.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.episen.si.ing1.pds.backend.server.db.orm.eloquent.collection.CollectionModel;
import edu.episen.si.ing1.pds.backend.server.db.pool.DataSource;
import edu.episen.si.ing1.pds.backend.server.network.exchange.routes.CallBack;
import edu.episen.si.ing1.pds.backend.server.network.exchange.routes.Route;
import edu.episen.si.ing1.pds.backend.server.network.exchange.routes.RouteService;
import edu.episen.si.ing1.pds.backend.server.network.exchange.socket.SocketParams;
import edu.episen.si.ing1.pds.backend.server.test.models.Companies;
import edu.episen.si.ing1.pds.backend.server.utils.Properties;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;

public class RouteTest {
    public static void main(String[] args) throws Exception {
        /*RouteService.INSTANCE.get("/tot/me", new CallBack() {
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
        RouteService.INSTANCE.get("/tot/:me", CompanyController.class, "index");
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
        System.out.println(me);*/
        DataSource ds = new DataSource(20, 200);
        SocketParams.setConnection(ds.getConnectionPool().getConnection());

        final Companies model = new Companies();
        CollectionModel<Companies> all = model.all();
        CollectionModel<Companies> except = all.except("address");
        System.out.println(all);
        System.out.println(except);
    }
}
