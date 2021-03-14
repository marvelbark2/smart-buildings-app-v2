package edu.episen.si.ing1.pds.client.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.episen.si.ing1.pds.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SocketClient {
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private ObjectMapper mapper = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger(SocketClient.class.getName());

    public SocketClient(Socket socket) {
        this.socket = socket;

        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
            writer = new PrintWriter(outputStream, true);

            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMessage(String msg) {
        writer.println(msg);
        readMessage();
    }
    public void readMessage() {
        try {
            String request = reader.readLine();
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
    public void update() {
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
    public void delete() {
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
}
