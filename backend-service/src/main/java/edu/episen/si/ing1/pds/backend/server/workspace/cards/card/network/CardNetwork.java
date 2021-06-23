package edu.episen.si.ing1.pds.backend.server.workspace.cards.card.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.datatype.jsr310.*;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.Network;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.card.models.CardRequest;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.card.models.CardsResponse;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.card.services.CardService;
import edu.episen.si.ing1.pds.backend.server.network.Request;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.card.services.ICardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
* Class Network
* Handling requests recevied from client for card services
* */
public class CardNetwork implements Network {
    private final ICardService<CardRequest, CardsResponse> service;
    private final PrintWriter writer;
    private final Connection connection;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger(CardNetwork.class.getName());

    public CardNetwork(Connection connection, PrintWriter writer) {
        this.connection = connection;
        service = new CardService(connection);
        this.writer = writer;
        mapper.registerModule(new JavaTimeModule());
    }

    public void execute(Request request) {
        String event = request.getEvent();
        service.setCompanyId(request.getCompanyId());
        if(event.equals("card_list")) {
            try {
                List data = service.findAll();
                Map<String, Object> msgResponseT = Utils.responseFactory(data, "card_list");
                String reponseMsg = mapper.writeValueAsString(msgResponseT);
                writer.println(reponseMsg);
            } catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
            }
        }
        else if(event.equals("card_byid")) {
            try {
                int card_id = request.getData().get("id").asInt();
                Optional card = service.findById(card_id);
                Object msg;
                if(card.isPresent()) {
                    msg = card.get();
                } else {
                    msg = "No record found";
                }
                Map responseBody = Map.of("card", msg);

                logger.info(responseBody.toString());

                Map<String, Object> msgResponseT = Utils.responseFactory(responseBody, "card_byid");
                String reponseMsg = mapper.writeValueAsString(msgResponseT);
                writer.println(reponseMsg);
            } catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
            }
        }
        else if(event.equals("card_insert")) {
            try {
                JsonNode data = request.getData();
                CardRequest cardRequest = mapper.treeToValue(data, CardRequest.class);
                boolean response = service.add(cardRequest);
                writer.println(mapper.writeValueAsString(Utils.responseFactory(response, "card_insert")));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        else if(event.equals("card_delete")) {
            try {
                JsonNode data = request.getData();
                CardRequest cardRequest = mapper.treeToValue(data, CardRequest.class);
                Boolean response = service.delete(cardRequest);
                writer.println(mapper.writeValueAsString(Utils.responseFactory(response, "card_delete")));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(event.equals("card_treeview")) {
            try {
                JsonNode data = request.getData();
                CardRequest cardRequest = mapper.treeToValue(data, CardRequest.class);
                logger.info(cardRequest.toString());
                ArrayNode response = service.treeView(cardRequest);
                writer.println(mapper.writeValueAsString(Utils.responseFactory(response, event)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (event.equals("card_access_list")) {
            JsonNode data = request.getData();
            List<Map> response = service.getItemAccessList(data.get("serialId").asText());
            try {
                writer.println(mapper.writeValueAsString(Utils.responseFactory(response, event)));
            } catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
            }
        }
        else if(event.equals("card_update")) {
            JsonNode data = request.getData();
            try {
                JsonNode card = data.get("card");
                CardRequest cardRequest = mapper.treeToValue(card, CardRequest.class);
                Integer card_id = data.get("card_id").asInt();
                ArrayNode spaces = (ArrayNode) data.get("workspaces");
                Boolean updateResponse = service.update(cardRequest, card_id);
                Boolean accessByCard = service.accessByCard(spaces, card_id);
                writer.println(mapper.writeValueAsString(Utils.responseFactory(updateResponse && accessByCard, event)));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(event.equals("card_lost")) {
            JsonNode data = request.getData();
            try {
                CardRequest cardRequest = mapper.treeToValue(data, CardRequest.class);
                JsonNode newCard = service.lostCard(cardRequest);
                writer.println(mapper.writeValueAsString(Utils.responseFactory(newCard, event)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(event.equals("card_activation")) {
            JsonNode data = request.getData();
            try {
                JsonNode card = data.get("card");
                Boolean action = data.get("action").asBoolean();
                CardRequest cardRequest = mapper.treeToValue(card, CardRequest.class);
                Boolean newCard = service.activeCard(cardRequest, action);
                writer.println(mapper.writeValueAsString(Utils.responseFactory(newCard, event)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
