package edu.episen.si.ing1.pds.backend.server.test.persistence;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;

public enum SqlConfig {
    Instance;

    String CREATE;
    String READ;
    String UPDATE;
    String DELETE;
    String ALL;

    SqlConfig() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        InputStream reader = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.yaml");
        try {
            JsonNode jsonNode = mapper.readTree(reader).get("sql");
            CREATE = jsonNode.get("CREATE").textValue();
            READ = jsonNode.get("READ").textValue();
            UPDATE = jsonNode.get("UPDATE").textValue();
            DELETE = jsonNode.get("DELETE").textValue();
            ALL = jsonNode.get("ALL").textValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
