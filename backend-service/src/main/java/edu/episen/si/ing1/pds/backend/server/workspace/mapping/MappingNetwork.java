package edu.episen.si.ing1.pds.backend.server.workspace.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.episen.si.ing1.pds.backend.server.network.Request;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappingNetwork {
    public MappingNetwork(Request request, Connection connection, PrintWriter writer) {
        
    	String event = request.getEvent();
        ObjectMapper mapper = new ObjectMapper();
        if(event.equalsIgnoreCase("building_list")) {
            try {
                List<Map> response = new ArrayList<>();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT DISTINCT buildings.name FROM workspace\n"
                		+ "INNER JOIN floors ON workspace.floor_number = floors.floor_number\n"
                		+ "INNER JOIN buildings ON floors.building_number = buildings.id_buildings");
                while (rs.next()) {
                    Map hm = new HashMap();
                   // hm.put("buildingid", rs.getInt("buildingid"));
                    hm.put("name", rs.getString("name"));
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
    }
}
