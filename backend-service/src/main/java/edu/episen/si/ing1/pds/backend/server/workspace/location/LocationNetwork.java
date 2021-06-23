package edu.episen.si.ing1.pds.backend.server.workspace.location;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import edu.episen.si.ing1.pds.backend.server.network.Request;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;

import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//file where we can find every request I can use in the client. 

public class LocationNetwork {

    public LocationNetwork(Request request, Connection connection, PrintWriter writer) throws Exception {
        String event = request.getEvent();
        Integer companyId = request.getCompanyId();
        final ObjectMapper mapper = new ObjectMapper();

        switch (event) {
        //request made in order to get a random openspace from the table workspace
            case "random_offer_openspace":
            	List<Map<String,Object>> offeropenspace = new ArrayList<>();
            	String query3 = "SELECT * FROM workspace where workspace_state='disponible' and workspace_type='Espace Ouvert' order by random() limit 1";
            	PreparedStatement statement1 = connection.prepareStatement(query3);
            	 ResultSet rs3 = statement1.executeQuery();
            	 if(rs3.next()) {
            		 Map<String, Object> mapOP = new HashMap<>();
            		 mapOP.put("id",rs3.getInt("id_workspace"));
            		 mapOP.put("size",rs3.getInt("size"));
            		 mapOP.put("price",rs3.getInt("price"));
            		 mapOP.put("employee",rs3.getInt("max_person"));
            		 offeropenspace.add(mapOP);
            	 }
            		 Map response3 = Utils.responseFactory(offeropenspace, event);
                 String msg3 = mapper.writeValueAsString(response3);
                 writer.println(msg3);
                 break;
                 
                 //request made in order to get a ransom desk from the table workspace
            case "random_offer_desk":
            	List<Map<String,Object>> offerdesk = new ArrayList<>();
            	String query4 = "SELECT * FROM workspace where workspace_state='disponible' and workspace_type='Bureau' order by random() limit 1";
            	PreparedStatement statement2 = connection.prepareStatement(query4);
            	 ResultSet rs4 = statement2.executeQuery();
            	 if(rs4.next()) {
            		 Map<String, Object> mapD = new HashMap<>();
            		 mapD.put("id",rs4.getInt("id_workspace"));
            		 mapD.put("size",rs4.getInt("size"));
            		 mapD.put("price",rs4.getInt("price"));
            		 mapD.put("employee",rs4.getInt("max_person"));
            		 offerdesk.add(mapD);
            	 }
            		 Map response4 = Utils.responseFactory(offerdesk, event);
                 String msg4 = mapper.writeValueAsString(response4);
                 writer.println(msg4);
                 break;
                 
            
            	                            
               
                
                  //request to get a random workspace from the table workspace
                 
            case "random_offer":
            	List<Map<String,Object>> offer = new ArrayList<>();
            	String query5 = "SELECT * FROM workspace where workspace_state='disponible' order by random() limit 1";
            	PreparedStatement statement3 = connection.prepareStatement(query5);
            	 ResultSet rs5 = statement3.executeQuery();
            	 if(rs5.next()) {
            		 Map<String, Object> mapOF = new HashMap<>();
            		 mapOF.put("id",rs5.getInt("id_workspace"));
            		 mapOF.put("size",rs5.getInt("size"));
            		 mapOF.put("price",rs5.getInt("price"));
            		 mapOF.put("employee",rs5.getInt("max_person"));
            		 offer.add(mapOF);
            	 }
            		 Map response5 = Utils.responseFactory(offer, event);
                 String msg5 = mapper.writeValueAsString(response5);
                 writer.println(msg5);
                 break;
                 
                 
               
                 
                 
            case "done_reservation":
            			String query = "UPDATE workspace SET workspace_state = 'indisponible' where id_workspace = ?";
            			//String nbreserv = "SELECT COUNT(*) FROM reservations";
            			Integer id_compa = request.getCompanyId();
            			
            			String queryInsert	= "INSERT INTO reservations (id_companies,id_workspace) VALUES(?,?)";
            			ArrayNode ids = (ArrayNode) request.getData().get("workspace_id");
            			
            			
            			PreparedStatement statement41 = connection.prepareStatement("SELECT max(reservation_number)+1 as max from reservations");
            			ResultSet resnb = statement41.executeQuery();
            			int reservnb = 0;
            			if(resnb.next()) {
            			int max =resnb.getInt("max");
            			reservnb = max;
            			}
            			
            			
            			
            			List<Integer> idsList = mapper.convertValue(ids, new TypeReference<List<Integer>>(){});
            			
            			for(Integer id: idsList) {
                			PreparedStatement statement4 = connection.prepareStatement(query);
                			statement4.setInt(1, id);
                			statement4.executeUpdate();
                			PreparedStatement statement42 = connection.prepareStatement("INSERT INTO reservations (id_companies,id_workspace,reservation_number) VALUES (?,?,?)");
                			statement42.setInt(1,id_compa);
                			statement42.setInt(2,id);
                			statement42.setInt(3, reservnb);
                			statement42.executeUpdate();
            			}
            			
            		break;
            	
            	
            	

                
                
                
            case "nb_reservation_list":
            	List<Map<String,Object>> numb_res = new ArrayList<>();
            	Integer id_compan = request.getCompanyId();
            	PreparedStatement statement8 = connection.prepareStatement("select count(*) as numb_r from reservations where id_companies = ?");
            	statement8.setInt(1, id_compan);
    			ResultSet rs8 = statement8.executeQuery();
    			 if(rs8.next()) {
            		 Map<String, Object> rep = new HashMap<>();
            		 rep.put("count",rs8.getInt("numb_r"));
            		 numb_res.add(rep);
    			 }
    			 Map response8 = Utils.responseFactory(numb_res, event);
    			 String msg8 = mapper.writeValueAsString(response8);
                 writer.println(msg8);
            	
    			break;
    			
            case "reservation_list":
            	Integer last_time = request.getData().get("last_id_res").asInt();
            	Integer id_company = request.getCompanyId();
            	List<Map<String,Object>> res = new ArrayList<>();
            	PreparedStatement statement9 = connection.prepareStatement("select  reservations.id_workspace,workspace.size,workspace.price,workspace.max_person,reservations.id_reservation,floors.building_number,floors.floor_number  from reservations inner join workspace on reservations.id_workspace = workspace.id_workspace  inner join floors on workspace.floor_number = id_floor\r\n" + 
            			"where id_companies = ? and id_reservation>? order by id_reservation asc limit 1");
            	statement9.setInt(1, id_company);
            	statement9.setInt(2, last_time);
            	ResultSet rs9 = statement9.executeQuery();
            	if(rs9.next()) {
           		 Map<String, Object> mapOF = new HashMap<>();
           		 mapOF.put("id",rs9.getInt("id_workspace"));
           		 mapOF.put("size",rs9.getInt("size"));
           		 mapOF.put("price",rs9.getInt("price"));
           		 mapOF.put("employee",rs9.getInt("max_person"));
           		 mapOF.put("id_reserv", rs9.getInt("id_reservation"));
           		 mapOF.put("fln",rs9.getInt("floor_number"));
           		mapOF.put("bln",rs9.getInt("building_number"));
           		 res.add(mapOF);
           		Map response9 = Utils.responseFactory(res, event);
                String msg9 = mapper.writeValueAsString(response9);
                writer.println(msg9);
           	 }
            	break;
                
            	
            case "kill_reservation":
            	List<Map<String,Object>> res_kill = new ArrayList<>();
            	Integer id_res_to_kill = request.getData().get("idres").asInt();
            	
            	System.out.print("text :"+id_res_to_kill);
            	PreparedStatement statement10 = connection.prepareStatement("UPDATE workspace SET workspace_state = 'disponible' WHERE id_workspace = ?");
            	PreparedStatement statement11= connection.prepareStatement("DELETE FROM reservations WHERE id_workspace = ?");
            	statement10.setInt(1, id_res_to_kill);
            	statement10.executeUpdate();
            	statement11.setInt(1, id_res_to_kill);
            	statement11.executeUpdate();
            	
            	break;
            	
            	
            	
                
            case "buildings_list":
            	List<Map> response = new ArrayList<>();                            
                Statement statement12 = connection.createStatement();
                ResultSet rs12 = statement12.executeQuery("SELECT * FROM buildings");
                while (rs12.next()) {
                    Map bMap=new HashMap();
                    bMap.put("name", rs12.getString("id_buildings"));
                    response.add(bMap);
                }
                Map response12=Utils.responseFactory(response, event);
                String msg12=mapper.writeValueAsString(response12);
                writer.println(msg12);
                break;
                
                
                
              
            case "floors_list":
            	List<Map> response13 = new ArrayList<>(); 
            	
                Integer building_name = request.getData().get("building_name").asInt();
               System.out.println(building_name);
            	Statement statement13 = connection.createStatement();
    			ResultSet rs13 = statement13.executeQuery("SELECT * FROM floors INNER JOIN buildings ON floors.building_number = buildings.id_buildings WHERE id_buildings = "+ building_name/*"'Batiment 1'*/+"");
                while (rs13.next()) {
                    Map fMap=new HashMap();
                    fMap.put("number", rs13.getString("floor_number"));
                    response13.add(fMap);
                }
                Map rsp13=Utils.responseFactory(response13, event);
                String msg13=mapper.writeValueAsString(rsp13);
                writer.println(msg13);
                break;
                
                
                
              
            case "numb_workspace":
            	List<Map> response14 = new ArrayList<>(); 
            	Integer bui = request.getData().get("building_nb").asInt();
                Integer flo = request.getData().get("floor_nb").asInt();
                Statement statement14 = connection.createStatement();
    			ResultSet rs14 = statement14.executeQuery("select count(*)as nb_wsp from workspace inner join floors on workspace.floor_number=floors.id_floor where floors.floor_number ="+flo+" and floors.building_number="+bui+"");
    			if (rs14.next()) {
                    Map fMap=new HashMap();
                    fMap.put("nb_wp", rs14.getInt("nb_wsp"));
                    response14.add(fMap);
                }
    			 Map rsp14=Utils.responseFactory(response14, event);
                 String msg14=mapper.writeValueAsString(rsp14);
                 writer.println(msg14);
    			break;
    			
            case "wp_esp":
            	List<Map> response15 = new ArrayList<>(); 
            	Integer buil = request.getData().get("building_nb").asInt();
                Integer floo = request.getData().get("floor_nb").asInt();
                Statement statement15 = connection.createStatement();
    			ResultSet rs15 = statement15.executeQuery("select * from workspace inner join floors on workspace.floor_number=floors.id_floor where floors.floor_number ="+floo+" and floors.building_number="+buil+" order by id_workspace asc");
    			while (rs15.next()) {
    				Map wpMap=new HashMap();
                    wpMap.put("state", rs15.getString("workspace_state"));
                    wpMap.put("numb", rs15.getInt("id_workspace"));
                    wpMap.put("type",rs15.getString("workspace_type"));
                    response15.add(wpMap);
    			}
    			 Map rsp15=Utils.responseFactory(response15, event);
                 String msg15=mapper.writeValueAsString(rsp15);
                 writer.println(msg15);
            	break;
    }
}
}

