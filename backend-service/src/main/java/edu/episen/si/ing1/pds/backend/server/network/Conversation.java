package edu.episen.si.ing1.pds.backend.server.network;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.episen.si.ing1.pds.backend.server.pool.PoolFactory;
import edu.episen.si.ing1.pds.backend.server.test.persistence.Repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Conversation implements Runnable  {
    private Socket socket;
    private int clientId;
    private boolean active = true;
    private Repository repository;
	private Logger logger = LoggerFactory.getLogger(Conversation.class.getName());

    public Conversation(Socket socket, int clientId) {
        this.socket = socket;
        this.clientId = clientId;
    }
    
    public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public boolean isActive() { return active; }

    @Override
    public void run() {
        try {
            //Init Reading
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            // Init Writting
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);

            //Client IP
            String ip = socket.getRemoteSocketAddress().toString();
            String msg = "Client " + clientId + " has been successfully connected with " + ip;

            //write on server Log
            logger.info(msg);

            // write Map message
            Map<String, Object> message = responseFactory("Connected Successfully");

            // Serialize map's message to json
            ObjectMapper mapper = new ObjectMapper();
            String successMessage = mapper.writeValueAsString(message);

            //send success message to client
            writer.println(successMessage);

            //Request Handling
            String request;
            while ((request = reader.readLine()) != null && active) {
                Request requestObj = mapper.readValue(request, Request.class);
                String event = requestObj.getEvent();
                // TODO: (Replace || handling) with crud operations
                switch (event) {
                    case "create":
                        logger.info("Client {} asking for create", clientId);
                        JsonNode node = requestObj.getData();
                        String[] values = new String[]{node.get("name").asText(), node.get("email").asText(), node.get("telephone").asText() };
                        Map<String, Object> createResponse = responseFactory(repository.create(values));
                        String createMessage = mapper.writeValueAsString(createResponse);
                        writer.println(createMessage);
                        break;

                    case "update":
                        logger.info("Client {} asking for update", clientId);
                        JsonNode node2 = requestObj.getData();
                        String[] valuesUpdate = new String[]{node2.get("name").asText(), node2.get("email").asText(), node2.get("telephone").asText() };
                        Map<String, Object> updateResponse = responseFactory(repository.update(getLastId(), valuesUpdate));
                        String updateMessage = mapper.writeValueAsString(updateResponse);
                        writer.println(updateMessage);
                        break;

                    case "delete":
                        logger.info("Client {} asking for delete", clientId);
                        Map<String, Object> deleteResponse = responseFactory(repository.delete(getLastId()));
                        String deleteMessage = mapper.writeValueAsString(deleteResponse);
                        writer.println(deleteMessage);
                        break;

                    case "read":
                        logger.info("Client {} asking for read", clientId);
                        Map<String, Object> readResponse = responseFactory(repository.readAll().get(repository.readAll().size() - 1));
                        String readMessage = mapper.writeValueAsString(readResponse);
                        writer.println(readMessage);               
                        break;
                }
            }
            socket.close();
            while(socket.isClosed()) {
                active = false;
                logger.info("The user {} disconnected", clientId);
                Server.getClients().remove(this);
                break;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private Map<String, Object> responseFactory(Object msg) {
        Map<String, Object> message = new HashMap<>();
        message.put("success", String.valueOf(true));
        message.put("message", msg);
        if(msg instanceof List) {
            message.put("dataType", "list");
        } else if(msg instanceof Map) {
            message.put("dataType", "map");
        } else if (msg instanceof String) {
            message.put("dataType", "string");
        } else {
            message.put("dataType", "object");
        }
        return message;
    }

    private int getLastId() {
        List<Map<String, Object>> all = repository.readAll();
        return (int) all.get(all.size() - 1).get("id");
    }
}
