package edu.episen.si.ing1.pds.backend.server.test;

import edu.episen.si.ing1.pds.backend.server.network.exchange.routes.CallBack;
import edu.episen.si.ing1.pds.backend.server.network.exchange.routes.RouteService;
import edu.episen.si.ing1.pds.backend.server.network.server.Server;
import edu.episen.si.ing1.pds.backend.server.db.orm.builder.Builder;
import edu.episen.si.ing1.pds.backend.server.db.orm.builder.DbColumn;
import edu.episen.si.ing1.pds.backend.server.db.orm.builder.DbTable;
import edu.episen.si.ing1.pds.backend.server.test.models.Companies;
import edu.episen.si.ing1.pds.backend.server.utils.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

public class Test {
    static final Logger logger = LoggerFactory.getLogger(Test.class.getName());
    public static void main(String[] args) throws Exception {
        logger.info("Test started");

        DbTable t = new DbTable("contacts");

        DbColumn col1 = new DbColumn("id", 2L);
        DbColumn col2 = new DbColumn("name", "TOTO");

        t.addCol(col1);
        t.addCol(col2);

        Builder sqlBuilder = new Builder(t);
        String select = sqlBuilder.select();
        logger.info("SQL query: {}", select);

        String sqlSelectB = Builder.selectBuilder().from(t).select(col1).select(col2).orderBy(col1).limit(1).build();
        logger.info("SQL query2: {}", sqlSelectB);

        String sqlInsert = Builder.insertBuilder().into(t).build();
        logger.info("SQL query3: {}", sqlInsert);
        Properties.executor = Executors.newCachedThreadPool();



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
        RouteService.INSTANCE.get("/tot/:me", CompanyController.class, "index");
        RouteService.INSTANCE.get("/bre/me/:mo", new CallBack() {
            @Override
            public Object callBack(Object... t) {
                return t[0];
            }
        });
       /* Companies c = new Companies();
        List<Companies> l = c.all();
        logger.info("list: {}", l);
        */
        Class.forName(Companies.class.getName());
        System.out.println(Companies.getNameTOTO());
        Server.init()
                .testMode(false)
                .setConfigVar("toto")
                .setEncrypted(false)
                .setDs(6)
               // .setHandler(new TestHandler())
                .thread(true)
                .serve();
        Properties.executor.shutdownNow().forEach(System.out::println);
    }
}
