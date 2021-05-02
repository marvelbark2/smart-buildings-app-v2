package edu.episen.si.ing1.pds.backend.server.workspace.shared;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.episen.si.ing1.pds.backend.server.network.Request;
import edu.episen.si.ing1.pds.backend.server.pool.PoolFactory;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;

import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class SystemLog {
    public SystemLog(Request request, Connection connection, Socket socket, PrintWriter writer) throws Exception {
        String event = request.getEvent();
        if(event.equals("system_logs")) {
            Map<String, Object> logs = new HashMap<>();
            logs.put("connection", connection.isClosed());
            logs.put("socket", socket.isClosed());
            logs.put("pool", PoolFactory.Instance.pool.poolSize());
            ObjectMapper mapper = new ObjectMapper();
            String response = mapper.writeValueAsString(Utils.responseFactory(logs, event));
            writer.println(response);
        }
    }
}
