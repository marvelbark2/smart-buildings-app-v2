package edu.episen.si.ing1.pds.backend.server.pool.config;

import com.fasterxml.jackson.databind.JsonNode;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;

import java.io.IOException;

public enum SqlConfig {
    Instance;
    private JsonNode node;

    SqlConfig() {
        try {
            JsonNode configNode = Utils.getConfigNode();
            node = configNode.get("sql");
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

    public String getRoleSql(String field) {
        return node.get("roles").get(field).asText();
    }

}
