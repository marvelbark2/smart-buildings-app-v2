package edu.episen.si.ing1.pds.backend.serveur;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackendServices {
    private final static Logger logger = LoggerFactory.getLogger(BackendServices.class.getName());

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
        if (commandLine.hasOption("testMode"))
            iMaxConnection = Integer.parseInt(commandLine.getOptionValue("maxConnection"));

        logger.info("Backend Service is running (testMode = {}) , (maxconnection = {).", itestMode, iMaxConnection);

    }
}







