package edu.episen.si.ing1.pds.backend.server.db.pool.config;

import com.fasterxml.jackson.databind.JsonNode;
import edu.episen.si.ing1.pds.backend.server.utils.Properties;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public enum DBConfig {
    Instance;
    private final Logger logger = LoggerFactory.getLogger(DBConfig.class.getName());
    public String HOST;
    public String USER;
    public String PASS;
    public String DRIVER;

    DBConfig() {
        try {
            JsonNode dbNode = Utils.getConfigNode();
            JsonNode db = dbNode.get("db");
            JsonNode jsonNode;
            if (Properties.testMode) {
                jsonNode = db.get("dev");
            } else {
                jsonNode = db.get("prod");
            }
            HOST = jsonNode.get("url").asText();
            USER = jsonNode.get("user").asText();
            PASS = jsonNode.get("pass").asText();
            DRIVER = db.get("driverClass").asText();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
