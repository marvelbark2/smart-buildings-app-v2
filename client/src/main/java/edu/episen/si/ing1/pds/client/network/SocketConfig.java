package edu.episen.si.ing1.pds.client.network;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import edu.episen.si.ing1.pds.client.utils.Utils;

import java.io.File;

public enum SocketConfig {
    Instance;

    public String HOST;
    public int PORT;

    SocketConfig() {
     
    }
    
    public void setEnv(Boolean isTestMode){
    	ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        File reader = Utils.getFileContent("SMARTBUILDCLIENTCONFIG");
        try {
            JsonNode jsonNode;
            if(isTestMode) {
            	jsonNode = mapper.readTree(reader).get("socket").get("dev");
            }
            else {
            	jsonNode = mapper.readTree(reader).get("socket").get("prod");
            }
            PORT = jsonNode.get("port").asInt();
            HOST = jsonNode.get("host").textValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
