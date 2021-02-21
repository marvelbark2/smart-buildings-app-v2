package edu.episen.si.ing1.pds.backend.serveur;

import edu.episen.si.ing1.pds.backend.serveur.persistence.Contacts;
import org.apache.commons.cli.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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

        if(itestMode && iMaxConnection > 0) {
            List<Boolean> results = new ArrayList<>();
            Contacts contacts = new Contacts(iMaxConnection);

            String[] c1 = {"Christiant", "Christiant@upec.fr", "0708237209"};
            Boolean result1 = contacts.create(c1);
            results.add(result1);

            String[] c2 = {"Younes", "Youness@upec.fr", "0708237299"};
            Boolean result2 = contacts.update(1,c2);
            results.add(result2);

            Boolean result3 = contacts.delete(2);
            results.add(result3);

            contacts.closeConnection();

            logger.info("Data : {}, {}", contacts.read(1), results);
        } else {
            logger.info("Backend Service is running (testMode = " + itestMode + ") , (maxconnection = " + iMaxConnection + "}.");
        }

    }
}







