package edu.episen.si.ing1.pds.backend.server.utils;

import java.io.File;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    public static String generateStringId(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        final Random RANDOM = new SecureRandom();
        final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    public static File getFileContent(String varEnv) {
        String varValue = System.getenv(varEnv);
        return new File(varValue);
    }

    public static Map<String, Object> responseFactory(Object msg, String event) {
        Map<String, Object> message = new HashMap<>();
        message.put("success", true);
        message.put("message", msg);
        message.put("event", event);
        if(msg instanceof List) {
            message.put("dataType", "list");
        } else if(msg instanceof Map) {
            message.put("dataType", "map");
        } else if (msg instanceof String) {
            message.put("dataType", "string");
        } else {
            message.put("dataType", "object");
        }
        return message;
    }
}
