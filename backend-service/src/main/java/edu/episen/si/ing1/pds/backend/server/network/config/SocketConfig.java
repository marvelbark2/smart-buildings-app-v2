package edu.episen.si.ing1.pds.backend.server.network.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;

import java.io.File;

public enum SocketConfig {
    Instance;
    public int PORT;

    SocketConfig() {
        try {
            JsonNode configNode = Utils.getConfigNode();
            JsonNode jsonNode = configNode.get("socket");
            PORT = jsonNode.get("port").asInt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
