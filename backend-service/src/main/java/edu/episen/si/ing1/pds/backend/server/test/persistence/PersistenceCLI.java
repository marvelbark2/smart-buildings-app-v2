package edu.episen.si.ing1.pds.backend.server.test.persistence;

import edu.episen.si.ing1.pds.backend.server.pool.DataSource;
import edu.episen.si.ing1.pds.backend.server.pool.config.DBConfig;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class PersistenceCLI {
    final static Logger logger = LoggerFactory.getLogger(PersistenceCLI.class.getName());
    public static void main(String[] args) throws Exception {
        logger.info("Persistence Test Started");
        DBConfig.Instance.setEnv(false);
        DataSource ds = new DataSource(10);
        Connection connection = ds.getConnection();

        cardPersist(connection);
//        try {
//            deskPersistence(connection);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
        logger.info("Persistence Test Done");
//        for (int i = 0; i < 10; i++) {
//            logger.info(generateSerialNumber());
//        }

    }

    static void floorPersistence(Connection connection) throws Exception {
        for (int i = 2; i <= 6; i++) {
            for (int j = 1; j <= 3; j++) {
                String query = "INSERT INTO floors (buildingid, abbreviation, designation, enabled) VALUES (" + i +", ?, ?, true)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1,"ETG" + i);
                statement.setString(2,"Etage " + j);
                statement.executeUpdate();
                logger.info("building {} floor {} persisted", i, j);
            }
        }
    }

    static void deskPersistence(Connection connection) throws Exception {
        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 3; j++) {
                String query = "INSERT INTO desks (floorId, abbreviation, designation, enabled) VALUES (" + i +", ?, ?, true)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1,"SL" + j);
                statement.setString(2,"Salle " + j);
                statement.executeUpdate();
                logger.info("Floor {} Desk {} persisted", i, j);
            }
        }
    }

    static void userPersistence(Connection connection) throws Exception {
        String[] users = {"John Doe", "Patrice Low", "Joe Show", "XSS", "Sql Injection"};
        for (String user : users) {
            String query = "INSERT INTO users (user_uid, name) values (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, Utils.generateStringId(15));
            statement.setString(2, user);
            statement.executeUpdate();
        }
    }

    static void cardPersist(Connection connection) throws Exception {
        List<Integer> userIds = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT userId FROM users");
        while (rs.next())
            userIds.add(rs.getInt(1));

        for (Integer user: userIds) {
            String query = "INSERT INTO cards (carduid, userid, expirable) values (?, ?, false) ";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, generateSerialNumber());
            statement.setInt(2, user);
            statement.executeUpdate();
        }
    }

    static String generateSerialNumber() {
        String randomString = Utils.generateStringId(20).toUpperCase(Locale.ROOT);
        StringBuilder builder = new StringBuilder(randomString);
        for (int i = 0; i < builder.length(); i++) {
            if(i % 5 == 0 && i != 0)
                builder.insert(i, "-");
        }
        return builder.toString();
    }
}
