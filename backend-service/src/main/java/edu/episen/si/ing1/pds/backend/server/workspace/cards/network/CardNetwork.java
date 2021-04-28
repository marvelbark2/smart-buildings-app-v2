package edu.episen.si.ing1.pds.backend.server.workspace.cards.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.*;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.models.CardRequest;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.services.CardService;
import edu.episen.si.ing1.pds.backend.server.network.Request;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;
import edu.episen.si.ing1.pds.backend.server.workspace.shared.Services;
import edu.episen.si.ing1.pds.backend.server.workspace.users.models.UsersRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CardNetwork {
    private Services service;
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
                Optional card = service.findById(request.getData().get("id").asLong());
                Object msg;
                if(card.isPresent()) {
                    msg = card.get();
                } else {
                    msg = "No record found";
                }
                Map<String, Object> msgResponseT = Utils.responseFactory(msg, "card_byid");
                String reponseMsg = mapper.writeValueAsString(msgResponseT);
                writer.println(reponseMsg);
            } catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
            }
        }
        else if(event.equals("card_insert")) {
            try {
                UsersRequest usersRequest = mapper.treeToValue(request.getData().get("user"), UsersRequest.class);
                CardRequest cardRequest = new CardRequest();
                cardRequest.setUser(usersRequest);
                cardRequest.setCardUId(request.getData().get("cardUId").asText());
                cardRequest.setExpirable(request.getData().get("expirable").asBoolean());
                String expireDate = request.getData().get("expireDate").asText();
                if(!expireDate.equals(null) && expireDate.length() > 0)
                    cardRequest.setExpiredDate(localDateParser(expireDate));
                boolean response = service.add(cardRequest);
                writer.println(mapper.writeValueAsString(Utils.responseFactory(response, "card_insert")));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        else if(event.equals("card_delete")) {
            try {
                JsonNode data = request.getData();
                UsersRequest usersRequest = new UsersRequest();
                usersRequest.setName(data.get("user").asText());
                CardRequest cardRequest = new CardRequest();
                cardRequest.setUser(usersRequest);
                cardRequest.setCardId(data.get("ID").asLong());
                cardRequest.setCardUId(data.get("Matricule").asText());
                cardRequest.setExpirable(data.get("Provisoire").asBoolean());

                if(data.get("Date d'expiration").asText().equals("Infini"))
                    cardRequest.setExpiredDate(null);
                else
                    cardRequest.setExpiredDate(localDateParser(data.get("Date d'expiration").asText()));
                Boolean response = service.delete(cardRequest);
                writer.println(mapper.writeValueAsString(Utils.responseFactory(response, "card_delete")));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (event.equals("card_access_list")) {
            JsonNode data = request.getData();
            CardService cardService = new CardService(connection);
            List<Map> response = cardService.getItemAccessList(data.get("serialId").asText());
            try {
                writer.println(mapper.writeValueAsString(Utils.responseFactory(response, event)));
            } catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }


    private LocalDate localDateParser(String date) {
        String[] dateArr = date.split("/");
        return LocalDate.of(Integer.parseInt(dateArr[2]), Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[0]));
    }
}
