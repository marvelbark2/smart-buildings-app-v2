package edu.episen.si.ing1.pds.client.test;

import edu.episen.si.ing1.pds.client.network.SocketClient;
import edu.episen.si.ing1.pds.client.network.SocketConfig;
import edu.episen.si.ing1.pds.client.utils.Utils;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;

public class SocketTestCli {
    private final static Logger logger = LoggerFactory.getLogger(SocketTestCli.class.getName());
    public static void main(String[] args) throws Exception {
        logger.info("Client Started");

        final Options options = new Options();

        final Option testMode = Option.builder().longOpt("testMode").build();
        options.addOption(testMode);

        final Option localEnv = Option.builder().longOpt("localEnv").build();
        options.addOption(localEnv);

        final Option crudOperation = Option.builder()
                .longOpt("use-crud-operation")
                .desc("Crud operation list args operation separated by |")
                .numberOfArgs(Option.UNLIMITED_VALUES)
                .argName("operation list")
                .valueSeparator('|')
                .hasArgs()
                .build();
        options.addOption(crudOperation);

        final Option valuesOption = Option.builder()
                .longOpt("values")
                .desc("values map contains data binded")
                .argName("values data")
                .type(Map.class)
                .hasArgs()
                .build();
        options.addOption(valuesOption);

        final CommandLineParser parser = new DefaultParser();
        final CommandLine commandLine = parser.parse(options, args);;

        boolean iTestMode = false;
        if(commandLine.hasOption(testMode.getLongOpt()))
            iTestMode = true;

        boolean iLocalEnv = false;
        if(commandLine.hasOption(localEnv.getLongOpt()))
            iLocalEnv = true;

        SocketConfig.Instance.setEnv(iLocalEnv);

        if(iTestMode) {
            SocketTest test = new SocketTest();
            test.testSockets();
        } else {
            String[] operations = commandLine.getOptionValues(crudOperation.getLongOpt());
            Map<String, String> values = Utils.toMap(commandLine.getOptionValue(valuesOption.getLongOpt()));

            SocketConfig config = SocketConfig.Instance;
            InetAddress host = InetAddress.getByName(config.HOST);

            Socket socket = new Socket(host, config.PORT);
            SocketClient client = new SocketClient(socket);

            client.readMessage();
            Arrays.stream(operations).forEach(operation -> {
                switch(operation) {
                    case "insert":
                        client.create(values); break;
                    case "update":
                        client.update(values); break;
                    case "select":
                        client.read(values); break;
                    case "delete":
                        client.delete(values); break;
                }
            });
        }

    }
}
