package edu.episen.si.ing1.pds.client.swing.cards.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.utils.Utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserRequests {
    public static List<Map> all() {
        Request request = new Request();
        request.setEvent("user_list");
        return (List<Map>) Utils.sendRequest(request).getMessage();
    }
     public static List<Map> hasCard() {
        Request request = new Request();
        request.setEvent("user_list_hascard");
        return (List<Map>) Utils.sendRequest(request).getMessage();
    }

    public static JsonNode findUserInfo(Map user) {
        Request request = new Request();
        request.setEvent("user_find");
        request.setData(user);
        Response response = Utils.sendRequest(request);
        LinkedHashMap<String, Object> data = (LinkedHashMap<String, Object>) response.getMessage();
        return new ObjectMapper().valueToTree(data);
    }
    public static Boolean deleteUser(Map user) {
        Request request = new Request();
        request.setEvent("user_delete");
        request.setData(user);
        Response response = Utils.sendRequest(request);
        return (Boolean) response.getMessage();
    }

    public static Boolean insertUser(Map data) {
        Request request = new Request();
        request.setEvent("user_insert");
        request.setData(data);
        Response response = Utils.sendRequest(request);
        Boolean isInserted = (Boolean) response.getMessage();
        return isInserted;
    }
    public static Boolean updateUser(Map data) {
        Request request = new Request();
        request.setEvent("user_update");
        request.setData(data);
        Response response = Utils.sendRequest(request);
        Boolean isUpdared = (Boolean) response.getMessage();
        return isUpdared;
    }
}
