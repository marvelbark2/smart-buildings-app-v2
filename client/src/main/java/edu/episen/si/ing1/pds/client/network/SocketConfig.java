package edu.episen.si.ing1.pds.client.network;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.InputStream;

public enum SocketConfig {
    Instance;

    public String HOST;
    public int PORT;

    SocketConfig() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        InputStream reader = Thread.currentThread().getContextClassLoader().getResourceAsStream("Config.yaml");
        try {
            JsonNode jsonNode = mapper.readTree(reader).get("socket").get("dev");
            PORT = jsonNode.get("port").asInt();
            HOST = jsonNode.get("host").textValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
