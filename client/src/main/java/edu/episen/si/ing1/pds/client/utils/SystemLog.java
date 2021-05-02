package edu.episen.si.ing1.pds.client.utils;

import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

public class SystemLog extends TimerTask {
    private final Logger logger = LoggerFactory.getLogger(SystemLog.class.getName());
    @Override
    public void run() {
        Request request = new Request();
        request.setEvent("system_logs");
        Response response = Utils.sendRequest(request);
        logger.info(response.getMessage().toString());
    }
}
