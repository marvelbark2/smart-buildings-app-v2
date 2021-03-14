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
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        InputStream reader = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.yaml");
        try {
            JsonNode jsonNode = mapper.readTree(reader);
            HOST = jsonNode.get("db").get("prod").get("url").textValue();
            USER = jsonNode.get("db").get("prod").get("user").textValue();
            PASS = jsonNode.get("db").get("prod").get("pass").textValue();
            DRIVER = jsonNode.get("db").get("driverClass").textValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
