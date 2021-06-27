package edu.episen.si.ing1.pds.backend.server.network;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.episen.si.ing1.pds.backend.server.network.exchange.models.Request;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;
import edu.episen.si.ing1.pds.backend.server.utils.aes.AESUtils;
import edu.episen.si.ing1.pds.backend.server.workspace.Bootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;


public class Conversation implements Runnable {
    private final Socket socket;
    private final int clientId;
    private boolean active = true;
    private Connection connection;
    private PrintWriter writer;
	private final Logger logger = LoggerFactory.getLogger(Conversation.class.getName());

    public Conversation(Socket socket, int clientId) {
        this.socket = socket;
        this.clientId = clientId;
    }
    
    public void setConnection(Connection connection) {
		this.connection = connection;
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
            writer = new PrintWriter(outputStream, true);

            //Client IP
            String ip = socket.getRemoteSocketAddress().toString();
            String msg = "Client " + clientId + " has been successfully connected with " + ip;

            //write on server Log
            logger.info(msg);

            // Serialize map's message to json
            ObjectMapper mapper = new ObjectMapper();

            //Request Handling
            String request;
            while ((request = reader.readLine()) != null && active) {
                // deserialization
                String decryptedRequest = AESUtils.decrypt(request);
                Request requestObj = mapper.readValue(decryptedRequest, Request.class);
                logger.info(request);
                logger.info(requestObj.toString());
                logger.info("Request ID: {}",requestObj.getRequestId());

                new Bootstrap(requestObj, connection, writer);

                Map<String, Object> endResponse = Utils.responseFactory("end", "end");
                String createMessage = mapper.writeValueAsString(endResponse);
                writer.println(AESUtils.encrypt(createMessage));
            }

            socket.close();

            if(socket.isClosed()) {
                active = false;
                logger.info("The user {} disconnected", clientId);
            }
        } catch (Exception e) {
            badResponseFactory(e);
            logger.error(e.getMessage(), e);
        }
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
            String errorMessage = mapper.writeValueAsString(message);
            writer.println(AESUtils.encrypt(errorMessage));
            stringVar.close();
            writer.close();
            socket.close();
        } catch (Exception ioException) {
            logger.error(ioException.getMessage(), ioException);
        }
    }

    public PrintWriter getWriter() {
        return writer;
    }
}
