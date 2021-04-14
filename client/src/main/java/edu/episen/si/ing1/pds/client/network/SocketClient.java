package edu.episen.si.ing1.pds.client.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;


public class SocketClient {
    private final Socket socket;
    private final BufferedReader reader;
    private PrintWriter writer;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger(SocketClient.class.getName());

    public SocketClient(Socket socket) {
        this.socket = socket;
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            writer = new PrintWriter(outputStream, true);
        } catch (IOException e) {
           logger.error(e.getMessage());
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
            logger.error(e.getLocalizedMessage());
        }
    }

    public void readMessage() {
        try {
            String request;
            while (true) {
                request = reader.readLine();
                if(request == null) {
                    break;
                }
                Response response = mapper.readValue(request, Response.class);
                if (response.isSuccess()) {
                    Object objt = response.getMessage();
                    logger.info(objt.toString());
                } else {
                    logger.error(response.getMessage().toString());
                }

                if(response.getMessage().equals("end"))
                    break;
            }
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
            close();
        }
    }

    public void create(Object values) {
        Request request = new Request();
        request.setEvent("create");
        request.setData(values);
        try {
            String requestMessage = mapper.writeValueAsString(request);
            sendMessage(requestMessage);
        } catch (JsonProcessingException e) {
            logger.error(e.getLocalizedMessage());
            close();
        }

    }

    public void read(Object values) {
        Request request = new Request();
        request.setEvent("read");
        request.setData(values);

        try {
            String requestMessage = mapper.writeValueAsString(request);
            sendMessage(requestMessage);
        } catch (JsonProcessingException e) {
            logger.error(e.getLocalizedMessage());
            close();
        }

    }

    public void update(Object values) {
        Request request = new Request();
        request.setEvent("update");
        request.setData(values);
        try {
            String requestMessage = mapper.writeValueAsString(request);
            sendMessage(requestMessage);
        } catch (JsonProcessingException e) {
            logger.error(e.getLocalizedMessage());
            close();
        }

    }

    public void delete(Object values) {
        Request request = new Request();
        request.setEvent("delete");
        request.setData(values);
        try {
            String requestMessage = mapper.writeValueAsString(request);
            sendMessage(requestMessage);
        } catch (JsonProcessingException e) {
            logger.error(e.getLocalizedMessage());
            close();
        }
    }
}
