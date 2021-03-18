package edu.episen.si.ing1.pds.backend.server.network;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.InputStream;

public enum SocketConfig {
    Instance;
    int PORT;

    SocketConfig() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        InputStream reader = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.yaml");
        try {
            JsonNode jsonNode = mapper.readTree(reader).get("socket");
            PORT = jsonNode.get("port").asInt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
