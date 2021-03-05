package edu.episen.si.ing1.pds.backend.serveur;

import edu.episen.si.ing1.pds.backend.serveur.pool.DataSource;
import edu.episen.si.ing1.pds.backend.serveur.test.TestPool;
import edu.episen.si.ing1.pds.backend.serveur.test.TestType;
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

        final Option testType = Option.builder().longOpt("testType").hasArg().build();
        options.addOption(testType);

        final Option testPoolSize = Option.builder().longOpt("testPoolSize").hasArg().build();
        options.addOption(testPoolSize);

        final CommandLineParser parser = new DefaultParser();
        final CommandLine commandLine = parser.parse(options, args);

        boolean itestMode = false;
        if (commandLine.hasOption("testMode"))
            itestMode = true;

        int iMaxConnection = 10;
        if (commandLine.hasOption("maxConnection"))
            iMaxConnection = Integer.parseInt(commandLine.getOptionValue("maxConnection"));

        TestType iTestType = null;
        if (commandLine.hasOption("testType"))
            iTestType = TestType.Instance.getType(Integer.parseInt(commandLine.getOptionValue("testType")));

        int ItestSize = 20;
        if (commandLine.hasOption("testPoolSize"))
            ItestSize = Integer.parseInt(commandLine.getOptionValue("testPoolSize"));

        if (iMaxConnection > 0) {
            DataSource ds = new DataSource(iMaxConnection);
            TestPool test = new TestPool(ds);
            try {
                if (!itestMode) {
                    if (iTestType == null) {
                        test.handleError();
                    } else if (iTestType.equals(TestType.Create)) {
                        test.create();
                    } else if (iTestType.equals(TestType.Read)) {
                        test.read();
                    } else if (iTestType.equals(TestType.Update)) {
                        test.update();
                    } else if (iTestType.equals(TestType.Delete)) {
                        test.delete();
                    } else if (iTestType.equals(TestType.Loop)) {
                        test.testPool(ItestSize);
                    }

                } else {
                    test.testModeTest();
                }

            } finally {
                ds.shutdownPool();
                System.exit(1);
            }

        } else {
            logger.info("Backend Service is running (testMode = " + itestMode + ") , (maxconnection = " + iMaxConnection
                    + "}.");
        }

    }
}
