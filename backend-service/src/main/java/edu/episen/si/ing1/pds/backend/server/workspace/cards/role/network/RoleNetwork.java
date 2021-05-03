package edu.episen.si.ing1.pds.backend.server.workspace.cards.role.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.episen.si.ing1.pds.backend.server.network.Request;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.role.models.RoleResponse;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.role.services.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class RoleNetwork {
    private final Logger logger = LoggerFactory.getLogger(RoleNetwork.class.getName());
    private final PrintWriter writer;
    private final ObjectMapper mapper = new ObjectMapper();
    private final RoleService service;

    public RoleNetwork(PrintWriter writer, Connection connection) {
        this.writer = writer;
        service = new RoleService(connection);
    }

    public void execute(Request request) {
        String event = request.getEvent();
        service.setCompanyId(request.getCompanyId());
        if(event.equals("role_list")) {
            List<RoleResponse> response = service.findAll();
            Map presented = Utils.responseFactory(response, event);
            try {
                String serialized = mapper.writeValueAsString(presented);
                writer.println(serialized);
            } catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
