package edu.episen.si.ing1.pds.client.swing.cards.card;

import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.utils.Utils;

import java.util.List;
import java.util.Map;

public class CardRequests {
    public static List<Map> fetchCarcardList() {
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

    public static Boolean updateCard(Map editData) {
        Request editReq = new Request();
        editReq.setEvent("card_update");
        editReq.setData(editData);
        Response ediRes = Utils.sendRequest(editReq);
        return (Boolean) ediRes.getMessage();
    }
}
