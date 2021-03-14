package edu.episen.si.ing1.pds.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.SocketClient;
import edu.episen.si.ing1.pds.client.network.SocketConfig;
import edu.episen.si.ing1.pds.client.roles.RoleType;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Client {
    private final static Logger logger = LoggerFactory.getLogger(Client.class.getName());

    public static void main(String[] args) throws Exception {
        final Options options = new Options();
        final Option testMode = Option.builder().longOpt("testMode").build();
        options.addOption(testMode);

        final Option userRole = Option.builder().longOpt("userRole").hasArg().build();
        options.addOption(userRole);

        final CommandLineParser parser = new DefaultParser();
        final CommandLine commandLine = parser.parse(options, args);

        boolean iTestMode = false;
        if (commandLine.hasOption("testMode"))
            iTestMode = true;

        RoleType roleType = RoleType.Instance.findRole(5);
        if (commandLine.hasOption("userRole")) {
            int id = Integer.parseInt(commandLine.getOptionValue("userRole"));
            roleType = RoleType.Instance.findRole(id);
        }

        if(iTestMode) {
            SocketConfig config = SocketConfig.Instance;
            Socket socket = new Socket(config.HOST, config.PORT);
            SocketClient client = new SocketClient(socket);
            client.readMessage();
            client.create();
            client.update();
            client.delete();
            client.read();
        }


        logger.info("Client is running (testMode = {}, under a {}", iTestMode, roleType);

    }
}
