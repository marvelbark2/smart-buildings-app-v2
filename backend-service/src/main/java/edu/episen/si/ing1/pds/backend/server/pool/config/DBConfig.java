package edu.episen.si.ing1.pds.backend.server.pool.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum DBConfig {
    Instance;
    public String HOST;
    public String USER;
    public String PASS;
    public String DRIVER;

    DBConfig() {

    }
    public void setEnv(boolean isTestMode) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        InputStream reader = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.yaml");
        try {
            JsonNode db = mapper.readTree(reader).get("db");
            JsonNode jsonNode;
            if(isTestMode) {
                jsonNode = db.get("dev");
            }else {
                jsonNode = db.get("prod");
            }
            HOST = jsonNode.get("url").textValue();
            USER = jsonNode.get("user").textValue();
            PASS = jsonNode.get("pass").textValue();
            DRIVER = db.get("driverClass").textValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
