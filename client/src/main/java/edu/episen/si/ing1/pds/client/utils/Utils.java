package edu.episen.si.ing1.pds.client.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.network.SocketFacade;

import java.io.*;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class Utils {

    private static int companyId;
    private static boolean guestPage = true;

    public static void setCompanyId(int companyId) {
        Utils.companyId = companyId;
        Utils.guestPage = false;
    }

    public static void logOut() {
        Utils.companyId = -1;
        Utils.guestPage = false;
    }

    public static int getCompanyId() {
        return companyId;
    }

    public static boolean isGuestPage() {
        return guestPage;
    }

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
    public static String generateSerialNumber() {
        String randomString = generateStringId(20).toUpperCase(Locale.ROOT);
        StringBuilder builder = new StringBuilder(randomString);
        for (int i = 0; i < builder.length(); i++) {
            if(i % 5 == 0 && i != 0)
                builder.insert(i, "-");
        }
        return builder.toString();
    }
    public static Response sendRequest(Request request) {
        PrintWriter writer = null;
        BufferedReader reader = null;
        Response response = null;
        try {
            String event = request.getEvent();
            ObjectMapper mapper = new ObjectMapper();
            Socket socket = SocketFacade.Instance.getSocket();
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String requestSerialized = mapper.writeValueAsString(request);
            writer.println(requestSerialized);
            while (true) {
                String req = reader.readLine();
                if(req == null)
                    break;
                Response res = mapper.readValue(req, Response.class);
                if(res.getEvent().equals("end"))
                    break;
                else if(res.getEvent().equals(event))
                    response = res;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
