package edu.episen.si.ing1.pds.client.test.sockets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import edu.episen.si.ing1.pds.client.network.SocketClient;
import edu.episen.si.ing1.pds.client.network.SocketConfig;
import edu.episen.si.ing1.pds.client.utils.Utils;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
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

        final Option simulationNumber = Option.builder().longOpt("simulation-number").hasArg().build();
        options.addOption(simulationNumber);

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

        final Option dataSample = Option.builder()
                .longOpt("json-sample-file")
                .desc("Json path file of data sample")
                .argName("Sample file")
                .hasArg()
                .build();
        options.addOption(dataSample);

        final CommandLineParser parser = new DefaultParser();
        final CommandLine commandLine = parser.parse(options, args);;

        boolean iTestMode = false;
        if(commandLine.hasOption(testMode.getLongOpt()))
            iTestMode = true;

        boolean iLocalEnv = false;
        if(commandLine.hasOption(localEnv.getLongOpt()))
            iLocalEnv = true;

        int iSimulationNumber = 15;
        if(commandLine.hasOption(simulationNumber.getLongOpt()))
            iSimulationNumber = Integer.parseInt(commandLine.getOptionValue(simulationNumber.getLongOpt()));

        SocketConfig.Instance.setEnv(iLocalEnv);

        if(iTestMode) {
            SocketTest test = new SocketTest();
            test.testSockets(iSimulationNumber);
        } else {
            String[] operations = commandLine.getOptionValues(crudOperation.getLongOpt());
            Object values = null;
            if(commandLine.hasOption(valuesOption.getLongOpt()))
                values = Utils.toMap(commandLine.getOptionValue(valuesOption.getLongOpt()));
            else if(commandLine.hasOption(dataSample.getLongOpt())) {
                ObjectMapper mapper = new ObjectMapper();
                File reader = new File(commandLine.getOptionValue(dataSample.getLongOpt()));
                ArrayNode node = (ArrayNode) mapper.readTree(reader).get("data");
                values = node;
            }
            SocketConfig config = SocketConfig.Instance;
            InetAddress host = InetAddress.getByName(config.HOST);

            Socket socket = new Socket(host, config.PORT);
            SocketClient client = new SocketClient(socket);

            //client.readMessage();
            Object finalValues = values;
            Arrays.stream(operations).forEach(operation -> {
                switch(operation) {
                    case "insert":
                        client.create(finalValues); break;
                    case "update":
                        client.update(finalValues); break;
                    case "select":
                        client.read(finalValues); break;
                    case "delete":
                        client.delete(finalValues); break;
                }
            });
            client.close();
        }

    }
}
