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
                ResultSet rs = statement.executeQuery("SELECT * FROM buildings");
                while (rs.next()) {
                    Map hm = new HashMap();
                    hm.put("buildingid", rs.getInt("buildingid"));
                    hm.put("abbreviation", rs.getString("abbreviation"));
                    response.add(hm);
                }
                Map responseMsg = Utils.responseFactory(response, event);
                String serializedMsg = mapper.writeValueAsString(responseMsg);
                writer.println(serializedMsg);

            } catch (Exception throwables) {
                throwables.printStackTrace();
            }

        }
    }
}
