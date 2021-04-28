package edu.episen.si.ing1.pds.backend.server;

import edu.episen.si.ing1.pds.backend.server.network.Server;
import edu.episen.si.ing1.pds.backend.server.pool.PoolFactory;
import edu.episen.si.ing1.pds.backend.server.pool.config.DBConfig;
import org.apache.commons.cli.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackendService {
    private final static Logger logger = LoggerFactory.getLogger(BackendService.class.getName());

    public static void main(String[] args) throws Exception {
        final Options options = new Options();
        final Option testMode = Option.builder().longOpt("testMode").build();
        options.addOption(testMode);

        final Option maxConnection = Option.builder().longOpt("maxConnection").hasArg().build();
        options.addOption(maxConnection);

        final CommandLineParser parser = new DefaultParser();
        final CommandLine commandLine = parser.parse(options, args);

        boolean itestMode = false;
        if (commandLine.hasOption("testMode"))
            itestMode = true;

        int iMaxConnection = 10;
        if (commandLine.hasOption("maxConnection"))
            iMaxConnection = Integer.parseInt(commandLine.getOptionValue("maxConnection"));

        boolean iNotReturnable = false;

        logger.info("Backend Service has been started");

        DBConfig.Instance.setEnv(itestMode);
        PoolFactory.Instance.setNotReturnable(iNotReturnable);


        Server serverSocket = new Server(iMaxConnection);
        serverSocket.serve();

    }
}
