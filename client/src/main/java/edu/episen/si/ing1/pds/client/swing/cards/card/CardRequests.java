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
}
