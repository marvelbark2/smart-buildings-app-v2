package edu.episen.si.ing1.pds.client.swing.cards.card;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.utils.Utils;

import java.util.List;
import java.util.Map;

public class CardRequests {
    private static ObjectMapper mapper = new ObjectMapper();

    public static List<Map> fetchCardList() {
        Request req = new Request();
        req.setEvent("card_list");
        Response response = Utils.sendRequest(req);
        return (List<Map>) response.getMessage();
    }

    public static Map<String, Object> fetchCardById(int cardId) {
        Request request = new Request();
        request.setEvent("card_byid");
        request.setData(Map.of(
                "id",
                cardId
                )
        );
        Response response = Utils.sendRequest(request);
        return  (Map<String, Object>) response.getMessage();
    }
    public static List<Map> getCardAccessList(Map data) {
        Request requestAccessList = new Request();
        requestAccessList.setEvent("card_access_list");
        requestAccessList.setData(data);
        Response response = Utils.sendRequest(requestAccessList);
        return (List<Map>) response.getMessage();
    }

    public static List<Map> cardTree(Map data) {
        Request request1 = new Request();
        request1.setEvent("card_treeview");
        data.remove("cardId");
        request1.setData(data);
        Response response1 = Utils.sendRequest(request1);
        return  (List<Map>) response1.getMessage();
    }

    public static Boolean updateCard(Map editData) {
        Request editReq = new Request();
        editReq.setEvent("card_update");
        editReq.setData(editData);
        Response ediRes = Utils.sendRequest(editReq);
        return (Boolean) ediRes.getMessage();
    }

    public static Boolean deleteCard(Map data) {
        Request request = new Request();
        request.setEvent("card_delete");
        if(data.containsKey("cardId")) {
            data.remove("cardId");
        }
        request.setData(data);
        Response response = Utils.sendRequest(request);
        return (Boolean) response.getMessage();
    }

    public static Boolean inserCard(Map data) {
        Request insertCardReq = new Request();
        insertCardReq.setEvent("card_insert");
        insertCardReq.setData(data);
        Response response = Utils.sendRequest(insertCardReq);
        return (Boolean) response.getMessage();
    }

    public static Boolean activeCard(Map data) {
        Request request = new Request();
        request.setEvent("card_activation");
        if(data.containsKey("cardId")) {
            data.remove("cardId");
        }
        request.setData(data);
        Response response = Utils.sendRequest(request);
        return (Boolean) response.getMessage();
    }

    public static JsonNode lostCard(Map data) {
        Request lostCardReq = new Request();
        data.remove("cardId");
        lostCardReq.setData(data);
        lostCardReq.setEvent("card_lost");
        Response response = Utils.sendRequest(lostCardReq);
        return  mapper.valueToTree(response.getMessage());
    }
}
