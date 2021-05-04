package edu.episen.si.ing1.pds.client.swing.cards.access;

import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.utils.Utils;

import java.util.List;
import java.util.Map;

public class AccessRequests {
    public static List<Map> getBuildingList() {
        Request request = new Request();
        request.setEvent("access_building_list");
        Response response = Utils.sendRequest(request);
        return (List<Map>) response.getMessage();
    }
    public static List<Map> getFloorList(int buildingId) {
        Request request = new Request();
        request.setEvent("access_floor_list");
        request.setData(Map.of("id_building", buildingId));
        Response response = Utils.sendRequest(request);
        return (List<Map>) response.getMessage();
    }
    public static List<Map> getWorkspaceList(int floor) {
        Request request = new Request();
        request.setEvent("access_workspace_list");
        request.setData(Map.of("id_floor", floor));
        Response response = Utils.sendRequest(request);
        return (List<Map>) response.getMessage();
    }
    public static List<Map> getEquipmentList(int workspace) {
        Request request = new Request();
        request.setEvent("access_equipment_list");
        request.setData(Map.of("id_workspace", workspace));
        Response response = Utils.sendRequest(request);
        return (List<Map>) response.getMessage();
    }
    public static Boolean isAccessibleToWS(int workspace, Map card) {
        Request request = new Request();
        request.setEvent("access_workspace_verify");
        card.remove("cardId");
        request.setData(Map.of("id_workspace", workspace, "card", card));
        Response response = Utils.sendRequest(request);
        return (Boolean) response.getMessage();
    }
    public static Boolean isAccessibleToEquip(int equip, Map card) {
        Request request = new Request();
        request.setEvent("access_equipment_verify");
        card.remove("cardId");
        request.setData(Map.of("id_equipment", equip, "card", card));
        Response response = Utils.sendRequest(request);
        return (Boolean) response.getMessage();
    }
}
