package edu.episen.si.ing1.pds.backend.server.workspace.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.episen.si.ing1.pds.backend.server.network.Request;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;

import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationNetwork {

    public LocationNetwork(Request request, Connection connection, PrintWriter writer) throws Exception {
        String event = request.getEvent();
        Integer companyId = request.getCompanyId();
        final ObjectMapper mapper = new ObjectMapper();

        switch (event) {
            case "location_building_list":
                List<Map<String, Object>> buildingList = new ArrayList<>();
                String query = "SELECT * FROM buildings";
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    Map<String, Object> hm = new HashMap<>();
                    hm.put("id", rs.getInt("id_buildings"));
                    hm.put("name", rs.getString("name"));

                    buildingList.add(hm);
                }
                Map responseList = Utils.responseFactory(buildingList, event);
                String msg = mapper.writeValueAsString(responseList);
                writer.println(msg);
                break;
            case "location_building_byid":
                Map<String, Object> building = new HashMap<>();
                Integer building_id = request.getData().get("building_id").asInt();
                String query2 = "SELECT * FROM buildings WHERE id_buildings = ?";
                PreparedStatement stmt = connection.prepareStatement(query2);
                stmt.setInt(1, building_id);
                ResultSet rs2 = stmt.executeQuery();
                if (rs2.next()) {
                    building.put("id", rs2.getInt("id_buildings"));
                    building.put("name", rs2.getString("name"));
                }
                Map responseById = Utils.responseFactory(building, event);
                String msgById = mapper.writeValueAsString(responseById);
                writer.println(msgById);
                break;
        }

    }
}
