package edu.episen.si.ing1.pds.backend.server.workspace.ConfigFenetre;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.sound.sampled.Clip;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.episen.si.ing1.pds.backend.server.network.Request;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;


public class ConfigWindowNetwork {

    private String id;

    public ConfigWindowNetwork(Request request, Connection connection, PrintWriter writer) throws Exception {
        String event = request.getEvent();
        ObjectMapper mapper = new ObjectMapper();
        if(event.equalsIgnoreCase("data_sensor _list1")) {
            try {
                List<Map> response = new ArrayList<>();
                JsonNode data = request.getData();
                int work_id = data.get("window_id").asInt();
                String sql = "select * from setting WHERE window_id="+id;
                String sql1 =  "select inside_temperature, sun_degree, outside_temperature from windows  inner join data_sensor d on id=window_id where id="+id;
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, request.getCompanyId());
                statement.setInt(2, work_id);
                ResultSet rs = statement.executeQuery();
                while(rs.next()) {
                    Map hm = new HashMap();
                    response.add(hm);
                }


                Map responseMsg = Utils.responseFactory(response, event);
                String serializedMsg = mapper.writeValueAsString(responseMsg);
                writer.println(serializedMsg);

            } catch (Exception throwables) {
                throwables.printStackTrace();
            }

        }
        else if(event.equalsIgnoreCase("windows_by_space_id")) {
            List<Map> response = new LinkedList<>();
            int spaceId = request.getData().get("space_id").asInt();
            String sql = "SELECT *,  case when (SELECT is_window FROM equipments WHERE equipments.id_equipments = ws.equipment_id) then true else false end as is_window FROM workspace_equipments ws WHERE  id_workspace = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, spaceId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                LinkedHashMap<String, Object> lhm = new LinkedHashMap<>();
                lhm.put("position_id", rs.getInt("id_workspace_equipments"));
                lhm.put("space_id", rs.getInt("id_workspace"));
                lhm.put("equipment_id", rs.getInt("equipment_id"));
                lhm.put("x", rs.getInt("gridx"));
                lhm.put("y", rs.getInt("gridy"));
                lhm.put("height", rs.getInt("gridheight"));
                lhm.put("width", rs.getInt("gridwidth"));
                lhm.put("state", rs.getString("etat"));
                lhm.put("is_window", rs.getBoolean("is_window"));
                response.add(lhm);
            }
            Map responseMsg=Utils.responseFactory(response, event);
            String serializedMsgString=mapper.writeValueAsString(responseMsg);
            writer.println(serializedMsgString);

        }
        else if(event.equalsIgnoreCase("data_sensor _list2")) {
            try {
                List<Map> response = new ArrayList<>();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("select window_state,status_blind,status_stain,inside_temperature,outside_temperature,sunzDegree from windows  inner join data_sensor  on id=window_id where id=+id");

                while(rs.next()) {
                    Map hm = new HashMap();
                    response.add(hm);
                }
                Map responseMsg=Utils.responseFactory(response, event);
                String serializedMsgString=mapper.writeValueAsString(responseMsg);
                writer.println(serializedMsgString);

            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
        }

    }


}
