package edu.episen.si.ing1.pds.backend.server.pool.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;

import java.io.File;
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
        File reader = Utils.getFileContent("SMARTBUILDCONFIG");
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
