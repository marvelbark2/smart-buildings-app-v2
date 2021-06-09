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
                 
                 
                 //request to put the state unavailable on each workspace which are in the offer selected by the user
           /* case "done_reservtion":
                Map<String, Object> reservat = new HashMap<>();
                Integer list_worksp = request.getData().get("list_workspace").asInt();
                Integer reserv_numb = request.getData().get("reserv_numb").asInt();
                Integer id_compa = request.getData().get("id_compa").asInt();
                Integer id_worksp = request.getData().get("id_worksp").asInt();
                String query6 = "UPDATE workspace SET workspace_state ='indisponible' where id_workspace= ?";
                String queryInsert	= "INSERT INTO reservations (reservation_number ,id_companies,id_workspace) VALUES(?,?,?)";
                PreparedStatement statement4 = connection.prepareStatement(query6);
                statement4.setInt(1, list_worksp);

                PreparedStatement stmtUpdate = connection.prepareStatement(queryInsert);
                stmtUpdate.setInt(1, reserv_numb);
                stmtUpdate.setInt(2, id_compa);
                stmtUpdate.setInt(3, id_worksp);

                statement4.executeUpdate();
                stmtUpdate.executeUpdate();

                Map response6 = Utils.responseFactory(reservat, event);
                String msg6 = mapper.writeValueAsString(response6);
                writer.println(msg6);
                break;*/
                 
                 
          /* case "done_reservtion":
            	
            	
try {
	

                Map data_loading=(Map) request.getData();
                System.out.println("ws "+(Integer)data_loading.get("workspace_id"));
                int op1 = connection.createStatement().executeUpdate("UPDATE workspace SET workspace_state = indisponible where id_workspace="+(Integer)data_loading.get("workspace_id"));
                Map<String,Object> responsee=new HashMap<String,Object>();
                List<Map> update=new ArrayList<Map>();
                Map<String,Object> hm=new HashMap<String,Object>();
                System.out.println("op1 "+op1);
                hm.put("updated",op1);
                update.add(hm);
                responsee.put("data",update);
                String response_string=mapper.writeValueAsString(responsee);
            } catch(Exception ev) {
            	ev.printStackTrace();
            }*/
                 
                 
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
            	
            	
            	
            	
//                Map<String, Object> reservat = new HashMap<>();
//               
//                Integer id_worksp = request.getData().get("workspace_id").asInt();
//                Integer list_size = request.getData().get("liste_size").asInt();
//                Integer reserv_numb = request.getData().get("reserv_numb").asInt();
//                Integer id_compa = request.getData().get("id_compa").asInt();
//            
//                
//               
//                String query6 = "UPDATE workspace SET workspace_state ='indisponible' where id_workspace= ?";
//                String queryInsert = "INSERT INTO reservations (reservation_number ,id_companies,id_workspace) VALUES(?,?,?)";
//                PreparedStatement statement4 = connection.prepareStatement(query6);
//                
//                statement4.setInt(1, id_worksp);
//
//                PreparedStatement stmtUpdate = connection.prepareStatement(queryInsert);
//                stmtUpdate.setInt(1, reserv_numb);
//                stmtUpdate.setInt(2, id_compa);
//                stmtUpdate.setInt(3, id_worksp);
//
//                statement4.executeUpdate();
//                stmtUpdate.executeUpdate();
//               
//                Map response6 = Utils.responseFactory(reservat, event);
//                String msg6 = mapper.writeValueAsString(response6);
//                writer.println(msg6);
//                break;
                
                //request made in order to get a full list of the workspace that have been booked by a company 
            /*case "reservation_list":
                Map<String, Object> list_reserv = new HashMap<>();
                Integer company = request.getData().get("company_id").asInt();
                String query7 = "SELECT * FROM reservations WHERE id_companies = ?";
                PreparedStatement statement5 = connection.prepareStatement(query7);
                statement5.setInt(1, company);
                ResultSet rs7 = statement5.executeQuery();
                while (rs7.next()) {
                    list_reserv.put("id", rs7.getInt("id_workspace"));
                }
                Map response7 = Utils.responseFactory(list_reserv, event);
                String msg7 = mapper.writeValueAsString(response7);
                writer.println(msg7);
                break;*/
              
                
                
                // request made to get the number of reservation made by a company
            /*case "nb_reservation_list":
                Map<String, Object> nb_reserva = new HashMap<>();
                Integer company1 = request.getData().get("company_id").asInt();
                String query8 = "SELECT count(*) FROM reservations WHERE id_companies = ?";
                PreparedStatement statement6 = connection.prepareStatement(query8);
                statement6.setInt(1, company1);
                ResultSet rs8 = statement6.executeQuery();
                if (rs8.next()) {
      
                }
                Map response8 = Utils.responseFactory(nb_reserva, event);
                String msg8 = mapper.writeValueAsString(response8);
                writer.println(msg8); 
                break;*/
                
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
            	PreparedStatement statement9 = connection.prepareStatement("select * from reservations inner join workspace on reservations.id_workspace = workspace.id_workspace\r\n" + 
            			"where id_companies = ? and id_reservation>? limit 1");
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
            	
            	
            	
                //request made in order to overwrite the state available on the status available. It also remove the workspace from the table reservation
            /*case "kill_reservation":
              Map<String, Object> ask_destroy = new HashMap<>();
                int reservationId = request.getData().get("reservation_id").asInt();
                int idW = request.getData().get("workspace_id").asInt();

                connection.prepareStatement("DELETE FROM reservations WHERE id_reservation = " + reservationId).executeUpdate();
                String query9 = "UPDATE workspace SET workspace_state ='disponible' where id_workspace = ?";
                PreparedStatement statement7 = connection.prepareStatement(query9);
                statement7.setInt(1, idW);
                statement7.executeUpdate();
                writer.println(Utils.responseFactory(true ,event));
                break;*/
                
                
                //request made to get the list of the Buildings especially the name
            case "buildings_list":
            	List<Map> response = new ArrayList<>();                            
                Statement statement12 = connection.createStatement();
                ResultSet rs12 = statement12.executeQuery("SELECT * FROM buildings");
                while (rs12.next()) {
                    Map bMap=new HashMap();
                    bMap.put("name", rs12.getString("name"));
                    response.add(bMap);
                }
                Map response12=Utils.responseFactory(response, event);
                String msg12=mapper.writeValueAsString(response12);
                writer.println(msg12);
                break;
                
                
                
                //request made to get the list of the floors in a buildings
            case "floors_list":
            	List<Map> response13 = new ArrayList<>();                            
                String building_id13 = request.getData().get("building_name").toString();
            	Statement statement13 = connection.createStatement();
    			ResultSet rs13 = statement13.executeQuery("SELECT * FROM floors INNER JOIN buildings ON floors.building_number = buildings.id_buildings WHERE buildings.name = " + building_id13);
                while (rs13.next()) {
                    Map fMap=new HashMap();
                    fMap.put("number", rs13.getInt("floor_number"));
                    response13.add(fMap);
                }
                Map rsp13=Utils.responseFactory(response13, event);
                String msg13=mapper.writeValueAsString(rsp13);
                writer.println(msg13);
                break;
                
                
                
                //request made in order to get the number of workspace in a floor
            case "numb_workspace":
            	List<Map<Map,Object>> response11 = new ArrayList<>();                            
                Statement statement100 = connection.createStatement();
                String building_id2 = request.getData().get("building_id").toString();
                Integer floor_id = request.getData().get("floor_id").asInt(); ;
                ResultSet rs110 = statement100.executeQuery("SELECT COUNT(*) FROM workspace INNER JOIN floors ON workspace.floor_number = floors.floor_number INNER JOIN buildings ON floors.building_number = buildings.id_buildings WHERE floors.floor_number = "+ floor_id +" AND  buildings.name =" + building_id2);
                while (rs110.next()) {
                    Map wMap=new HashMap();
                    wMap.put("wnumber", rs110.getInt("count"));
                    response11.add(wMap);
                }
                Map rsp120=Utils.responseFactory(response11, event);
                String msg120=mapper.writeValueAsString(rsp120);
                writer.println(msg120);
                break;
    }
}
}

