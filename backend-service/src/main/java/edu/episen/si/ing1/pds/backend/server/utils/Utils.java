package edu.episen.si.ing1.pds.backend.server.utils;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static Map<String, String> toMap(String value) {
        value = value.substring(1, value.length()-1);
        String[] keyValuePairs = value.split(",");
        Map<String,String> map = new HashMap<>();

        for(String pair : keyValuePairs)
        {
            String[] entry = pair.split("=");
            map.put(entry[0].trim(), entry[1].trim());
        }
        return map;
    }

    public static File getFileContent(String varEnv) {
        String varValue = System.getenv(varEnv);
        return new File(varValue);
    }
}
