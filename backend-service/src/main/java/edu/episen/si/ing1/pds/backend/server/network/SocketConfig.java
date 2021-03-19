package edu.episen.si.ing1.pds.backend.server.network;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;

import java.io.File;

public enum SocketConfig {
    Instance;
    int PORT;

    SocketConfig() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        File reader = Utils.getFileContent("SMARTBUILDCONFIG");
        try {
            JsonNode jsonNode = mapper.readTree(reader).get("socket");

            PORT = jsonNode.get("port").asInt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
