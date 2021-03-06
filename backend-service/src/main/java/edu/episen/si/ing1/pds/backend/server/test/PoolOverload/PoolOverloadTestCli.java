package edu.episen.si.ing1.pds.backend.server.test.PoolOverload;

import edu.episen.si.ing1.pds.backend.server.pool.DataSource;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class PoolOverloadTestCli {
    private final static Logger logger = LoggerFactory.getLogger(PoolOverloadTestCli.class.getName());

    public static void main(String[] args) {
        final Options options = new Options();

        final Option initialLoad = Option.builder()
                .longOpt("initial-load")
                .required()
                .desc("Number to init pool")
                .type(Integer.class)
                .hasArg()
                .build();
        options.addOption(initialLoad);

        final Option upTo = Option.builder()
                .longOpt("up-to")
                .required()
                .desc("Number to test pool (Overload pool)")
                .type(Integer.class)
                .hasArg()
                .build();
        options.addOption(upTo);

        final Option getConnectionIv = Option.builder()
                .longOpt("get-connection-interval")
                .required()
                .desc("How long to get connection")
                .type(Integer.class)
                .hasArg()
                .build();
        options.addOption(getConnectionIv);

        final Option fard = Option.builder()
                .longOpt("force-additionnal-request-duration")
                .required()
                .desc("How long to wait after any operation")
                .type(Number.class)
                .hasArg()
                .build();
        options.addOption(fard);

        final Option crudOperation = Option.builder()
                .longOpt("use-crud-operation")
                .required()
                .desc("Crud operation list")
                .numberOfArgs(Option.UNLIMITED_VALUES)
                .argName("CRUD")
                .valueSeparator('|')
                .hasArgs()
                .build();
        options.addOption(crudOperation);

        final Option retention = Option.builder()
                .longOpt("with-retention")
                .desc("If exist, return all connection to pool")
                .build();
        options.addOption(retention);

        final CommandLineParser parser = new DefaultParser();
        final CommandLine commandLine;
        DataSource ds = null;
        try {
            commandLine = parser.parse(options, args);
            int nInitPool = Integer.valueOf(commandLine.getOptionValue(initialLoad.getLongOpt()));
            int testCount = Integer.valueOf(commandLine.getOptionValue(upTo.getLongOpt()));
            int delayTime = Integer.valueOf(commandLine.getOptionValue(getConnectionIv.getLongOpt()));
            int additionalDelay = Integer.valueOf(commandLine.getOptionValue(fard.getLongOpt()));
            String[] operations = commandLine.getOptionValues(crudOperation.getLongOpt());
            ds = new DataSource(nInitPool, delayTime);
            ds.returnable(commandLine.hasOption(retention.getLongOpt()));
            TestPoolOverLoad testPool = new TestPoolOverLoad(ds);
            for (int i = 0; i < testCount; i++) {
                if(ds.poolSize() == 0)
                    logger.warn("Pool is empty !");
                Arrays.stream(operations).forEach(operation -> {
                    switch (operation) {
                        case "insert": testPool.create(); break;
                        case "update": testPool.update(); break;
                        case "select": testPool.read(); break;
                        case "delete": testPool.delete(); break;
                    }
                });
                logger.info("END {} {}", i, System.lineSeparator());
                Thread.sleep(additionalDelay);
            }

        } catch (ParseException | InterruptedException e) {
            logger.error(e.getMessage());
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp(PoolOverloadTestCli.class.getName(), options);
        } finally {
            ds.shutdownPool();
        }

    }
}
