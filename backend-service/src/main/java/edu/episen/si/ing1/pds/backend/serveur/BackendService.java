package edu.episen.si.ing1.pds.backend.serveur;

import edu.episen.si.ing1.pds.backend.serveur.db.DataSource;
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

        if (itestMode && iMaxConnection > 0) {
            List<Boolean> results = new ArrayList<>();
            DataSource ds = new DataSource(iMaxConnection);
            Contacts contacts = new Contacts(ds);

            for (int i = 0; i < 35; i++) {
                contacts.read(1);
                if (true) {
                    for (int j = 0; j < 2; j++) {
                        logger.info(i + " : " + j);
                        contacts.read(1);
                    }
                } else {
                    String[] c1 = {"Christiant2", "Christiant@upec.fr", "0708237209"};
                    Boolean result1 = contacts.create(c1);
                    results.add(result1);

                    String[] c2 = {"Youness", "Youness@upec.fr", "0708237299"};
                    Boolean result2 = contacts.update(1, c2);
                    results.add(result2);

                    Boolean result3 = contacts.delete(2);
                    results.add(result3);

                    logger.info("Data : {}, {}", contacts.read(1), results);
                }
            }

            ds.shutdownPool();
        } else {
            logger.info("Backend Service is running (testMode = " + itestMode + ") , (maxconnection = " + iMaxConnection + "}.");
        }

    }
}







