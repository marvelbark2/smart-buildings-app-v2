package edu.episen.si.ing1.pds.client.swing.ConfigFenetre;
import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WindowRequests {
	public static List<Map>  getWindowsByWS(int spaceId){
        Request request = new Request();
        request.setEvent("windows_by_space_id");
        Map<String, Integer> hm = new HashMap<>();
        hm.put("space_id", spaceId);
        request.setData(hm);
        Response response = Utils.sendRequest(request);
        return (List<Map>) response.getMessage();
    }

}
