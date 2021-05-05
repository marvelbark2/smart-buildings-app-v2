package edu.episen.si.ing1.pds.client.swing.cards.roles;

import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.utils.Utils;

import java.util.List;
import java.util.Map;

public class RoleRequests {
    public static List<Map> fetchRoleList() {
        Request request = new Request();
        request.setEvent("role_list");
        Response response = Utils.sendRequest(request);
        return (List<Map>) response.getMessage();
    }
    public static List<Map> fetchAccessRoleList(Map role) {
        Request request = new Request();
        request.setEvent("role_access_list");
        request.setData(role);
        Response response = Utils.sendRequest(request);
        return (List<Map>) response.getMessage();
    }
    public static Boolean updateRole(Map data) {
        Request request = new Request();
        request.setEvent("role_update");
        request.setData(data);
        Response response = Utils.sendRequest(request);
        return (Boolean) response.getMessage();
    }
}
