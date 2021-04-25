package edu.episen.si.ing1.pds.backend.server.network;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.episen.si.ing1.pds.backend.server.utils.Utils;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.network.CardNetwork;
import edu.episen.si.ing1.pds.backend.server.workspace.mapping.MappingNetwork;
import edu.episen.si.ing1.pds.backend.server.workspace.users.network.UsersNetwork;
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

            // write Map message
            Map<String, Object> message = Utils.responseFactory("Connected Successfully", "welcome");

            // Serialize map's message to json
            ObjectMapper mapper = new ObjectMapper();
            String successMessage = mapper.writeValueAsString(message);

            //send success message to client
            //writer.println(successMessage);

            //Request Handling
            String request;
            while ((request = reader.readLine()) != null && active) {
                Request requestObj = mapper.readValue(request, Request.class);
                logger.info(requestObj.toString());
                logger.info("Request ID: {}",requestObj.getRequestId());

                CardNetwork cardNetwork = new CardNetwork(connection, writer);
                cardNetwork.execute(requestObj);

                UsersNetwork usersNetwork = new UsersNetwork(connection, writer);
                usersNetwork.execute(requestObj);

                new MappingNetwork(requestObj, connection, writer);

                Map<String, Object> endResponse = Utils.responseFactory("end", "end");
                String createMessage = mapper.writeValueAsString(endResponse);
                writer.println(createMessage);
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
            String errprMessage = mapper.writeValueAsString(message);
            writer.println(errprMessage);
            stringVar.close();
            writer.close();
            socket.close();
        } catch (IOException ioException) {
            logger.error(ioException.getMessage(), ioException);
        }
    }

    public PrintWriter getWriter() {
        return writer;
    }
}
