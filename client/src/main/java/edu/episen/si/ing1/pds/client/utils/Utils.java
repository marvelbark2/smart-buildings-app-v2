package edu.episen.si.ing1.pds.client.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.network.SocketFacade;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.*;
import java.util.Timer;

public class Utils {

    private static int companyId;
    private static boolean guestPage = true;
    private static String companyName;

    public static void setCompanyId(int companyId, String companyName) {
        Utils.companyId = companyId;
        Utils.companyName = companyName;
        Utils.guestPage = false;
    }

    public static void logOut() {
        Utils.companyId = -1;
        Utils.guestPage = false;
    }

    public static int getCompanyId() {
        return companyId;
    }

    public static String getCompanyName() {
        return companyName;
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

    public static ImageIcon getImageIconFromResource(String path) {
        Image icon = Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource(path));
        return new ImageIcon(icon);
    }

    // INSERT INTo T ('test', 'id) VALUES (ee,eee),(), (), () ...  ;
    public static Response sendRequest(Request request) {
        PrintWriter writer;
        BufferedReader reader;
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
