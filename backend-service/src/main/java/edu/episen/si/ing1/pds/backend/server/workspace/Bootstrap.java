package edu.episen.si.ing1.pds.backend.server.workspace;

import edu.episen.si.ing1.pds.backend.server.network.Request;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.Network;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.access.network.AccessNetwork;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.card.network.CardNetwork;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.role.network.RoleNetwork;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.user.network.UsersNetwork;
import edu.episen.si.ing1.pds.backend.server.workspace.location.LocationNetwork;
import edu.episen.si.ing1.pds.backend.server.workspace.mapping.MappingNetwork;

import java.io.PrintWriter;
import java.sql.Connection;

public class Bootstrap {
    public Bootstrap(Request request, Connection connection, PrintWriter writer) throws Exception {
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
