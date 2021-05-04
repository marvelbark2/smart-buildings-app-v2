package edu.episen.si.ing1.pds.backend.server.workspace.mapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.episen.si.ing1.pds.backend.server.network.Request;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappingNetwork {
    public MappingNetwork(Request request, Connection connection, PrintWriter writer) throws SQLException, JsonProcessingException {
        
    	String event = request.getEvent();
        ObjectMapper mapper = new ObjectMapper();
        if(event.equalsIgnoreCase("mapping_list")) {
            try {
            List<Map> response = new ArrayList<>();
            JsonNode data = request.getData();
            int work_id = data.get("workspace_id").asInt();
            String sql = "select * from reservations INNER JOIN workspace ON workspace.id_workspace = reservations.id_workspace INNER JOIN workspace_equipments ON workspace.id_workspace = workspace_equipments.id_workspace WHERE reservations.id_companies = ? and reservations.id_workspace = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, request.getCompanyId());
            statement.setInt(2, work_id);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Map hm = new HashMap();
                hm.put("gridx", rs.getInt("gridx"));
                hm.put("gridy", rs.getInt("gridy"));
                hm.put("gridwidth", rs.getInt("gridwidth"));
                hm.put("gridheigth", rs.getInt("gridheight"));
                hm.put("equipment_id", rs.getInt("equipment_id"));
                hm.put("etat", rs.getString("etat"));

                response.add(hm);
            }
            Map responseMsg = Utils.responseFactory(response, event);
            String serializedMsg = mapper.writeValueAsString(responseMsg);
            writer.println(serializedMsg);

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        }
        else if(event.equalsIgnoreCase("floors_list")) {
        	try {
        		List<Map> response = new ArrayList<>();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT b.id_buildings, f.floor_number FROM workspace\n"
                		+ "join floors f on f.id_floor = workspace.floor_number\n"
                		+ "join buildings b on b.id_buildings = f.building_number;");
                while(rs.next()) {
                	Map hMap=new HashMap();
                	//hMap.put("building_number", rs.getInt("building_number"));
                	hMap.put("floor_number", rs.getInt("floor_number"));
                	response.add(hMap);
                }
                Map responseMsg=Utils.responseFactory(response, event);
                String serializedMsgString=mapper.writeValueAsString(responseMsg);
        		writer.println(serializedMsgString);
        		
        	} catch (Exception throwables) {
        		throwables.printStackTrace();
        	}
        }
        else if(event.equalsIgnoreCase("companies_list")) {
            try {
                List<Map> response = new ArrayList<>();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM companies");
                while (rs.next()) {
                    Map hMap=new HashMap();
                    hMap.put("id_companies", rs.getInt("id_companies"));
                    hMap.put("name", rs.getString("name"));
                    response.add(hMap);
                }
                Map responseMsg=Utils.responseFactory(response, event);
                String serializedMsgString=mapper.writeValueAsString(responseMsg);
                writer.println(serializedMsgString);

            } catch (Exception e ){
                e.printStackTrace();
            }
        }
        else if(event.equalsIgnoreCase("tree_list")){
        	 String sql = "SELECT b.name,b.id_buildings, f.id_floor, concat('Etage ', f.floor_number) as floor, w.workspace_label, w.id_workspace FROM workspace w join floors f on f.id_floor = w.floor_number join buildings b on f.building_number = b.id_buildings JOIN reservations r on w.id_workspace = r.id_workspace WHERE r.id_companies = ?";
             PreparedStatement statement = connection.prepareStatement(sql);
             statement.setInt(1, request.getCompanyId());
             ResultSet rs = statement.executeQuery();
             Map<Map, List> data = new HashMap();
        
             
             while (rs.next()) {
                 Map hm = new HashMap();
                 hm.put("name", rs.getString("name"));
                 hm.put("id", rs.getInt("id_buildings"));


                 Map<Map, List> floorContainer = new HashMap<>();
                 Map floor = new HashMap();
                 floor.put("floor", rs.getString("floor"));
                 floor.put("id", rs.getInt("id_floor"));

                 Map workspace = new HashMap();
                 workspace.put("id_workspace", rs.getInt("id_workspace"));
                 workspace.put("workspace_type", rs.getString("workspace_label"));


                 if(!floorContainer.containsKey(floor)) {
                     List<Map> workspaces = new ArrayList<>();
                     workspaces.add(workspace);
                     floorContainer.put(floor, workspaces);
                 } else {
                     floorContainer.get(floor).add(workspace);
                 }
                 if(!data.containsKey(hm)) {
                     List<Map> floors = new ArrayList<>();
                     floors.add(floorContainer);
                     data.put(hm , floors);
                 } else {
                     data.get(hm).add(floorContainer);
                 }
             };
             Map responseMsg = Utils.responseFactory(data, event);
             String serializedMsgString = mapper.writeValueAsString(responseMsg);
             writer.println(serializedMsgString);
              
        }
    }
}
