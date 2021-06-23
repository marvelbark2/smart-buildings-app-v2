package edu.episen.si.ing1.pds.backend.server.workspace.cards.access.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import edu.episen.si.ing1.pds.backend.server.network.Request;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.Network;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.access.service.AccessService;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.access.service.IAccessService;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.card.models.CardRequest;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Map;
/*
* Class to handle Access network's requests
* */
public class AccessNetwork implements Network {
    private final ObjectMapper mapper = new ObjectMapper();
    private final PrintWriter writer;
    private IAccessService service;
    public AccessNetwork(Connection connection, PrintWriter writer) {
        this.writer = writer;
        service = new AccessService(connection);
    }

    @Override
    public void execute(Request request) {
        int companyId = request.getCompanyId();
        String event = request.getEvent();
        service.setCompanyId(companyId);

        // grabbing access list of buildings
        if(event.equals("access_building_list")) {
            ArrayNode list = service.buildingList();
            Map<String, Object> responseFormatter = Utils.responseFactory(list, event);
            try {
                String serialized = mapper.writeValueAsString(responseFormatter);
                writer.println(serialized);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        // grabbing access list of floors
        else if(event.equals("access_floor_list")) {
            JsonNode data = request.getData();
            int building_id = data.get("id_building").asInt();
            ArrayNode list = service.floorList(building_id);
            Map<String, Object> responseFormatter = Utils.responseFactory(list, event);
            try {
                String serialized = mapper.writeValueAsString(responseFormatter);
                writer.println(serialized);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        // grabbing access list of workspaces
        else if(event.equals("access_workspace_list")) {
            JsonNode data = request.getData();
            int floor_id = data.get("id_floor").asInt();
            ArrayNode list = service.workspaceList(floor_id);
            Map<String, Object> responseFormatter = Utils.responseFactory(list, event);
            try {
                String serialized = mapper.writeValueAsString(responseFormatter);
                writer.println(serialized);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        // grabbing access list of equioments
        else if(event.equals("access_equipment_list")) {
            JsonNode data = request.getData();
            int workspace_id = data.get("id_workspace").asInt();
            ArrayNode list = service.equipmentList(workspace_id);
            Map<String, Object> responseFormatter = Utils.responseFactory(list, event);
            try {
                String serialized = mapper.writeValueAsString(responseFormatter);
                writer.println(serialized);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        // Verify if card has access to specific workspace
        else if(event.equals("access_workspace_verify")) {
            JsonNode data = request.getData();
            int workspace_id = data.get("id_workspace").asInt();
            JsonNode cardNode = data.get("card");
            try {
                CardRequest card = mapper.treeToValue(cardNode, CardRequest.class);
                Boolean canActivate = service.hasAccessToWorkspace(card, workspace_id);
                Map<String, Object> responseFormatter = Utils.responseFactory(canActivate, event);
                String serialized = mapper.writeValueAsString(responseFormatter);
                writer.println(serialized);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        // Verify if card has access to specific equipment
        else if(event.equals("access_equipment_verify")) {
            JsonNode data = request.getData();
            int equipment_id = data.get("id_equipment").asInt();
            JsonNode cardNode = data.get("card");
            try {
                CardRequest card = mapper.treeToValue(cardNode, CardRequest.class);
                Boolean canActivate = service.hasAccessToEquipment(card, equipment_id);
                Map<String, Object> responseFormatter = Utils.responseFactory(canActivate, event);
                String serialized = mapper.writeValueAsString(responseFormatter);
                writer.println(serialized);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}
