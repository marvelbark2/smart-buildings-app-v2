package edu.episen.si.ing1.pds.backend.server.test.socketTest;

import edu.episen.si.ing1.pds.backend.server.network.Server;
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

        logger.info("TestSocket Started");

        DBConfig.Instance.setEnv(iTestMode);
        PoolFactory.Instance.setNotReturnable(iNotReturnable);
        Server serverSocket = new Server(iMaxConnection);
        serverSocket.serve();
    }
}
