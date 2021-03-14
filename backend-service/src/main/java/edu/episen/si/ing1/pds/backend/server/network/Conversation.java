package edu.episen.si.ing1.pds.backend.server.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Conversation implements Runnable {
    private Socket socket;
    private int clientId;

    private Logger logger = LoggerFactory.getLogger(Conversation.class.getName());

    public Conversation(Socket socket, int clientId) {
        this.socket = socket;
        this.clientId = clientId;
    }

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

            //IP
            String ip = socket.getRemoteSocketAddress().toString();
            String msg = "Client " + clientId + " has been successfully connected with " + ip;

            //write on server Log
            logger.info(msg);

            // write Map message
            Map<String, String> message = responseFactory("Connected Successfully");

            // Serialize map's message to json
            ObjectMapper mapper = new ObjectMapper();
            String successMessage = mapper.writeValueAsString(message);

            //send success message to client
            writer.println(successMessage);

            //Request Handling
            while(true) {
                String request = reader.readLine();
                Request requestObj = mapper.readValue(request, Request.class);
                String event = requestObj.getEvent();

                switch (event) {
                    case "create":
                        logger.info("Client {} asking for create", clientId);
                        Map<String, String> createResponse = responseFactory("created Successfully");
                        String createMessage = mapper.writeValueAsString(createResponse);
                        writer.println(createMessage);
                        break;

                    case "update":
                        logger.info("Client {} asking for update", clientId);
                        Map<String, String> updateResponse = responseFactory("updated Successfully");
                        String updateMessage = mapper.writeValueAsString(updateResponse);
                        writer.println(updateMessage);
                        break;

                    case "delete" :
                        logger.info("Client {} asking for delete", clientId);
                        Map<String, String> deleteResponse = responseFactory("deleted Successfully");
                        String deleteMessage = mapper.writeValueAsString(deleteResponse);
                        writer.println(deleteMessage);
                        break;

                    case "read" :
                        logger.info("Client {} asking for read", clientId);
                        Map<String, String> readResponse = responseFactory("read Successfully");
                        String readMessage = mapper.writeValueAsString(readResponse);
                        writer.println(readMessage);
                        break;


                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private Map responseFactory(String msg) {
        Map<String, String> message = new HashMap<>();
        message.put("success", String.valueOf(true));
        message.put("message", msg);
        return  message;
    }
}
