package edu.episen.si.ing1.pds.client.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SocketClient {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private final Logger logger = LoggerFactory.getLogger(SocketClient.class.getName());

    public SocketClient(Socket socket) {
        this.socket = socket;
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            writer = new PrintWriter(outputStream, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        reader = new BufferedReader(inputStreamReader);

    }

    public void sendMessage(String msg) {
        writer.println(msg);
        readMessage();

    }

    public void close() {
        try {
            writer.close();
            reader.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void readMessage() {
        try {
            String request = reader.readLine();
            ObjectMapper mapper = new ObjectMapper();
            Response response = mapper.readValue(request, Response.class);
            if (response.isSuccess()) {
            	Object objt = response.getMessage();
                logger.info(objt.toString());
            } else {
                logger.error(response.getMessage().toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void create(Map<String, String> values) {
        Request request = new Request();
        request.setEvent("create");
        request.setData(values);

        ObjectMapper mapper = new ObjectMapper();
        try {
            String requestMessage = mapper.writeValueAsString(request);
            sendMessage(requestMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public void read(Map<String, String> values) {
        Request request = new Request();
        request.setEvent("read");
        request.setData(values);

        ObjectMapper mapper = new ObjectMapper();
        try {
            String requestMessage = mapper.writeValueAsString(request);
            sendMessage(requestMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public void update(Map<String, String> values) {
        Request request = new Request();
        request.setEvent("update");
        request.setData(values);

        ObjectMapper mapper = new ObjectMapper();
        try {
            String requestMessage = mapper.writeValueAsString(request);
            sendMessage(requestMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public void delete(Map<String, String> values) {
        Request request = new Request();
        request.setEvent("delete");
        request.setData(values);

        ObjectMapper mapper = new ObjectMapper();
        try {
            String requestMessage = mapper.writeValueAsString(request);
            sendMessage(requestMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
