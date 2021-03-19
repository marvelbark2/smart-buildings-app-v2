package edu.episen.si.ing1.pds.backend.server.test.socketTest;

import edu.episen.si.ing1.pds.backend.server.network.Server;
import edu.episen.si.ing1.pds.backend.server.network.SocketConfig;
import edu.episen.si.ing1.pds.backend.server.pool.PoolFactory;
import edu.episen.si.ing1.pds.backend.server.pool.config.DBConfig;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketsTest {
    static final Logger logger = LoggerFactory.getLogger(SocketsTest.class.getName());
    public static void main(String[] args) throws Exception {
        final Options options = new Options();

        final Option testMode = Option.builder()
                .longOpt("testMode")
                .desc("Is a test mode")
                .build();
        options.addOption(testMode);

        final Option notReturnable = Option.builder()
                .longOpt("without-retention")
                .desc("do not return connection to pool")
                .build();
        options.addOption(notReturnable);

        final Option getConnectionIv = Option.builder()
                .longOpt("get-connection-interval")
                .desc("How long to get connection")
                .type(Integer.class)
                .argName("number value en msec")
                .hasArg()
                .build();
        options.addOption(getConnectionIv);

        final Option fard = Option.builder()
                .longOpt("force-additionnal-request-duration")
                .desc("How long to wait after any operation")
                .type(Number.class)
                .hasArg()
                .argName("number value en msec")
                .build();
        options.addOption(fard);

        final Option maxConnection = Option.builder().longOpt("maxConnection").hasArg().build();
        options.addOption(maxConnection);

        final CommandLineParser parser = new DefaultParser();
        final CommandLine commandLine = parser.parse(options, args);

        boolean iTestMode = false;
        if(commandLine.hasOption("testMode"))
            iTestMode = true;

        boolean iNotReturnable = true;
        if(commandLine.hasOption("without-retention"))
            iNotReturnable = false;

        int iMaxConnection = 10;
        if (commandLine.hasOption("maxConnection"))
            iMaxConnection = Integer.parseInt(commandLine.getOptionValue("maxConnection"));

        int delayTime = 1_000;
        if (commandLine.hasOption("get-connection-interval"))
            delayTime = Integer.parseInt(commandLine.getOptionValue("get-connection-interval"));

        int additionalDelay = 3_000;
        if (commandLine.hasOption("force-additionnal-request-duration"))
            additionalDelay = Integer.parseInt(commandLine.getOptionValue("force-additionnal-request-duration"));

        logger.info("TestSocket Started");
        DBConfig.Instance.setEnv(iTestMode);
        SocketConfig.Instance.setDelayTime(additionalDelay);
        PoolFactory.Instance.setNotReturnable(iNotReturnable);
        PoolFactory.Instance.setDelayTime(delayTime);
        Server serverSocket = new Server(iMaxConnection);
        serverSocket.start();
    }
}
