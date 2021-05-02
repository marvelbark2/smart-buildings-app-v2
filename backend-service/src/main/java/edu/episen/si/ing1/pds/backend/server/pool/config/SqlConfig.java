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

    private JsonNode node;

    SqlConfig() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        File reader = Utils.getFileContent("SMARTBUILDCONFIG");
        try {
            node = mapper.readTree(reader).get("sql");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getUserSql(String field) {
        return node.get("users").get(field).asText();
    }
    public String getCardSql(String field) {
        return node.get("cards").get(field).asText();
    }
    public String getRoleSql(String field) { return node.get("roles").get(field).asText(); }

}
