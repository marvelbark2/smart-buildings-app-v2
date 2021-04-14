package edu.episen.si.ing1.pds.backend.server.workspace.users.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.episen.si.ing1.pds.backend.server.network.Request;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;
import edu.episen.si.ing1.pds.backend.server.workspace.shared.Services;
import edu.episen.si.ing1.pds.backend.server.workspace.users.services.UsersService;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Map;
import java.util.Optional;

public class UsersNetwork {
    private Services service;
    private final PrintWriter writer;
    private final ObjectMapper mapper = new ObjectMapper();

    public UsersNetwork(Connection connection, PrintWriter writer) {
        this.writer = writer;
        service = new UsersService(connection);
    }

    public void execute(Request request) {
        String event = request.getEvent();
        if(event.equals("user_list")) {
            try {
                Map<String, Object> usersCollection = Utils.responseFactory(service.findAll(), "user_list");
                String collectionSerialized = mapper.writeValueAsString(usersCollection);
                writer.println(collectionSerialized);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        else if(event.equals("user_byid")) {
            try {
                Optional card = service.findById(request.getData().get("id").asLong());
                Object msg;
                if(card.isPresent()) {
                    msg = card.get();
                } else {
                    msg = "No record found";
                }
                Map<String, Object> msgResponseT = Utils.responseFactory(msg, "user_byid");
                String reponseMsg = mapper.writeValueAsString(msgResponseT);
                System.out.println(reponseMsg);
                writer.println(reponseMsg);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

}
