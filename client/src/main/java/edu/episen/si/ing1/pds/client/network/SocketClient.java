package edu.episen.si.ing1.pds.client.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SocketClient {
    private Socket socket;
    private final Logger logger = LoggerFactory.getLogger(SocketClient.class.getName());

    public SocketClient(Socket socket) {
        this.socket = socket;
    }

    public void sendMessage(String msg) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);
            writer.println(msg);
            readMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void readMessage() {
        try {
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String request = reader.readLine();
            ObjectMapper mapper = new ObjectMapper();
            Response response = mapper.readValue(request, Response.class);
            if(response.isSuccess()) {
                logger.info(response.getMessage());
            } else {
                logger.error(response.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void create() {
        Map<String, String> data = new HashMap<>();
        data.put("id", "23");

        Request request = new Request();
        request.setEvent("create");
        request.setData(data);

        ObjectMapper mapper = new ObjectMapper();
        try {
            String requestMessage = mapper.writeValueAsString(request);
            sendMessage(requestMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
    public void read() {
        Map<String, String> data = new HashMap<>();
        data.put("id", "23");

        Request request = new Request();
        request.setEvent("read");
        request.setData(data);

        ObjectMapper mapper = new ObjectMapper();
        try {
            String requestMessage = mapper.writeValueAsString(request);
            sendMessage(requestMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
    public void update() {
        Map<String, String> data = new HashMap<>();
        data.put("id", "23");

        Request request = new Request();
        request.setEvent("update");
        request.setData(data);

        ObjectMapper mapper = new ObjectMapper();
        try {
            String requestMessage = mapper.writeValueAsString(request);
            sendMessage(requestMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
    public void delete() {
        Map<String, String> data = new HashMap<>();
        data.put("id", "23");

        Request request = new Request();
        request.setEvent("delete");
        request.setData(data);

        ObjectMapper mapper = new ObjectMapper();
        try {
            String requestMessage = mapper.writeValueAsString(request);
            sendMessage(requestMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
