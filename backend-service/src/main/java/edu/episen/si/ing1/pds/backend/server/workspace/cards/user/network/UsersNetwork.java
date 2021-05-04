package edu.episen.si.ing1.pds.backend.server.workspace.cards.user.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.datatype.jsr310.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import edu.episen.si.ing1.pds.backend.server.network.Request;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.Network;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.user.models.UsersResponse;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.user.services.IUsersService;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.user.models.UsersRequest;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.user.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Map;
import java.util.Optional;

public class UsersNetwork implements Network {
    private IUsersService<UsersRequest, UsersResponse> service;
    private final PrintWriter writer;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger(UsersNetwork.class.getName());

    public UsersNetwork(Connection connection, PrintWriter writer) {
        this.writer = writer;
        service = new UsersService(connection);
        mapper.registerModule(new JavaTimeModule());
    }

    public void execute(Request request) {
        String event = request.getEvent();
        service.setCompanyId(request.getCompanyId());
        if(event.equals("user_list")) {
            try {
                Map<String, Object> usersCollection = Utils.responseFactory(service.findAll(), "user_list");
                String collectionSerialized = mapper.writeValueAsString(usersCollection);
                logger.info("Receiving user_list request");
                writer.println(collectionSerialized);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        else if(event.equals("user_byid")) {
            try {
                Optional user = service.findById(request.getData().get("id").asInt());
                Object msg;
                if(user.isPresent()) {
                    msg = user.get();
                } else {
                    msg = "No record found";
                }
                Map<String, Object> msgResponseT = Utils.responseFactory(msg, "user_byid");
                String reponseMsg = mapper.writeValueAsString(msgResponseT);
                writer.println(reponseMsg);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        else if(event.equals("user_insert")) {
            try {
                JsonNode data = request.getData();
                UsersRequest usersRequest = mapper.treeToValue(data, UsersRequest.class);
                usersRequest.setUserUId(Utils.generateStringId(15));
                Boolean isUserInserted = service.add(usersRequest);
                String response = mapper.writeValueAsString(Utils.responseFactory(isUserInserted, event));
                writer.println(response);
                logger.info(response);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(event.equals("user_delete")) {
            try{
                JsonNode data = request.getData();
                UsersRequest usersRequest = mapper.convertValue(data, UsersRequest.class);
                Boolean userInfo = service.delete(usersRequest);
                String response = mapper.writeValueAsString(Utils.responseFactory(userInfo, event));
                writer.println(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(event.equals("user_find")) {
            try{
                JsonNode data = request.getData();
                UsersRequest usersRequest = mapper.convertValue(data, UsersRequest.class);
                JsonNode userInfo = service.findUser(usersRequest);
                String response = mapper.writeValueAsString(Utils.responseFactory(userInfo, event));
                writer.println(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(event.equals("user_list_hascard")) {
            try {
                ArrayNode list = service.usersHasCard();
                String response = mapper.writeValueAsString(Utils.responseFactory(list, event));
                writer.println(response);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

}
