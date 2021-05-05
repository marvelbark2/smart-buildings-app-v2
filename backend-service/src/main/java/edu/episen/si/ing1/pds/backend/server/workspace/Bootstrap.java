package edu.episen.si.ing1.pds.backend.server.workspace;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.episen.si.ing1.pds.backend.server.network.Request;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.Network;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.access.network.AccessNetwork;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.card.network.CardNetwork;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.role.network.RoleNetwork;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.user.network.UsersNetwork;
import edu.episen.si.ing1.pds.backend.server.workspace.location.LocationNetwork;
import edu.episen.si.ing1.pds.backend.server.workspace.mapping.MappingNetwork;

import java.io.PrintWriter;
import java.sql.*;
import java.util.*;

public class Bootstrap {
    public Bootstrap(Request request, Connection connection, PrintWriter writer) throws Exception {

        if(request.getEvent().equalsIgnoreCase("companies_list")) {
            final ObjectMapper mapper = new ObjectMapper();
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
                Map responseMsg = Utils.responseFactory(response, request.getEvent());
                String serializedMsgString = mapper.writeValueAsString(responseMsg);
                writer.println(serializedMsgString);

            } catch (Exception e ){
                e.printStackTrace();
            }
        }
        Network cardNetwork = new CardNetwork(connection, writer);
        cardNetwork.execute(request);

        Network usersNetwork = new UsersNetwork(connection, writer);
        usersNetwork.execute(request);

        Network roleNetwork = new RoleNetwork(writer, connection);
        roleNetwork.execute(request);

        Network accessNetwork = new AccessNetwork(connection, writer);
        accessNetwork.execute(request);

        new MappingNetwork(request, connection, writer);

        new LocationNetwork(request, connection, writer);
    }
}
