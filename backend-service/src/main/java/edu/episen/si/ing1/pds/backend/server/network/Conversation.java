package edu.episen.si.ing1.pds.backend.server.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ArrayNode;
import edu.episen.si.ing1.pds.backend.server.test.persistence.Repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Conversation implements Runnable {
    private final Socket socket;
    private final int clientId;
    private boolean active = true;
    private Repository repository;
	private final Logger logger = LoggerFactory.getLogger(Conversation.class.getName());

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
            Thread.currentThread().setName("client-thread-" + clientId);
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
                logger.info("Request ID: {}",requestObj.getRequestId());
                switch (event) {
                    case "create":
                        logger.info("Client {} asking for create", clientId);
                        JsonNode node = requestObj.getData();
                        if(!(node instanceof ArrayNode)) {
                            String[] values = new String[]{node.get("name").asText(), node.get("email").asText(), node.get("telephone").asText() };
                            Map<String, Object> createResponse = responseFactory("Client-"+ clientId + " - Create: " + repository.create(values));
                            String createMessage = mapper.writeValueAsString(createResponse);
                            writer.println(createMessage);
                        } else {
                            ArrayNode arrayNode = (ArrayNode) node;
                            arrayNode.forEach(nodeData -> {
                                String[] values = new String[]{nodeData.get("name").asText(), nodeData.get("email").asText(), nodeData.get("telephone").asText() };
                                Map<String, Object> createResponse = responseFactory("Client-"+ clientId + " - Create: " + repository.create(values));
                                String createMessage = null;
                                try {
                                    createMessage = mapper.writeValueAsString(createResponse);
                                } catch (JsonProcessingException e) {
                                    try {
                                        badResponseFactory(e);
                                        writer.close();
                                        reader.close();
                                    } catch (Exception ex) {
                                        logger.error(ex.getMessage(), ex);
                                    }
                                    e.printStackTrace();
                                }
                                writer.println(createMessage);
                            });
                        }

                        break;

                    case "update":
                        logger.info("Client {} asking for update", clientId);
                        JsonNode node2 = requestObj.getData();
                        if(!(node2 instanceof ArrayNode)) {
                            int updateId = getLastId();
                            if(node2.has("id"))
                                updateId = node2.get("id").asInt();
                            String[] valuesUpdate = new String[]{node2.get("name").asText(), node2.get("email").asText(), node2.get("telephone").asText() };
                            Map<String, Object> updateResponse = responseFactory("Update: " + repository.update(updateId, valuesUpdate));
                            String updateMessage = mapper.writeValueAsString(updateResponse);
                            writer.println(updateMessage);
                        } else {
                            ArrayNode arrayNode2 = (ArrayNode) node2;
                            arrayNode2.forEach(nodeUpdate -> {
                                int updateId = getLastId();
                                if(nodeUpdate.has("id"))
                                    updateId = nodeUpdate.get("id").asInt();
                                String[] valuesUpdate = new String[]{nodeUpdate.get("name").asText(), nodeUpdate.get("email").asText(), nodeUpdate.get("telephone").asText() };
                                Map<String, Object> updateResponse = responseFactory("Update: " + repository.update(updateId, valuesUpdate));
                                String updateMessage = null;
                                try {
                                    updateMessage = mapper.writeValueAsString(updateResponse);
                                } catch (JsonProcessingException e) {
                                    try {
                                        badResponseFactory(e);
                                        writer.close();
                                        reader.close();
                                    } catch (Exception ex) {
                                        logger.error(ex.getMessage(), ex);
                                    }
                                    e.printStackTrace();
                                }
                                writer.println(updateMessage);
                            });
                        }
                        break;

                    case "delete":
                        logger.info("Client {} asking for delete", clientId);
                        JsonNode deleteNode = requestObj.getData();
                        if(!(deleteNode instanceof ArrayNode)) {
                            int deleteId = getLastId();
                            if(deleteNode.has("id"))
                                deleteId = Integer.parseInt(deleteNode.get("id").asText());
                            Map<String, Object> deleteResponse = responseFactory("Delete: " + repository.delete(deleteId));
                            String deleteMessage = mapper.writeValueAsString(deleteResponse);
                            writer.println(deleteMessage);
                        } else {
                            ArrayNode nodeDelete = (ArrayNode) deleteNode;
                            nodeDelete.forEach(delete -> {
                                int deleteId = getLastId();
                                if(delete.has("id"))
                                    deleteId = Integer.parseInt(delete.get("id").asText());
                                Map<String, Object> deleteResponse = responseFactory("Delete: " + repository.delete(deleteId));
                                String deleteMessage = null;
                                try {
                                    deleteMessage = mapper.writeValueAsString(deleteResponse);
                                } catch (JsonProcessingException e) {
                                    try {
                                        badResponseFactory(e);
                                        writer.close();
                                        reader.close();
                                    } catch (Exception ex) {
                                        logger.error(ex.getMessage(), ex);
                                    }
                                    e.printStackTrace();
                                }
                                writer.println(deleteMessage);
                            });
                        }
                        break;

                    case "read":
                        logger.info("Client {} asking for read", clientId);
                        Map<String, Object> readResponse = responseFactory("Read: " + repository.readAll().get(repository.readAll().size() - 1));
                        String readMessage = mapper.writeValueAsString(readResponse);
                        writer.println(readMessage);               
                        break;
                }
                Map<String, Object> endResponse = responseFactory("end");
                String createMessage = mapper.writeValueAsString(endResponse);
                writer.println(createMessage);
            }

            socket.close();

            if(socket.isClosed()) {
                active = false;
                logger.info("The user {} disconnected", clientId);
                Server.getClients().remove(this);
            }
        } catch (Exception e) {
            badResponseFactory(e);
            logger.error(e.getMessage(), e);
        }
    }

    private Map<String, Object> responseFactory(Object msg) {
        Map<String, Object> message = new HashMap<>();
        message.put("success", true);
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

    private void badResponseFactory(Exception e) {
        Map<String, String> message = new HashMap<>();
        message.put("success", String.valueOf(false));
        message.put("dataType", "error");

        try {
            ObjectMapper mapper = new ObjectMapper();
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);

            StringWriter sWriter = new StringWriter();
            PrintWriter stringVar = new PrintWriter(sWriter, true);
            e.printStackTrace(stringVar);
            String error = sWriter.toString();

            message.put("message", "Server Error: " + error);;
            String errprMessage = mapper.writeValueAsString(message);

            writer.println(errprMessage);

            stringVar.close();
            writer.close();
            socket.close();
        } catch (IOException ioException) {
            logger.error(ioException.getMessage(), ioException);
        }
    }



    private int getLastId() {
        List<Map<String, Object>> all = repository.readAll();
        return (int) all.get(all.size() - 1).get("id");
    }
}
