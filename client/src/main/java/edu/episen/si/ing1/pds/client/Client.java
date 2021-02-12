package edu.episen.si.ing1.pds.client;

import edu.episen.si.ing1.pds.client.roles.RoleType;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {
    private final static Logger logger = LoggerFactory.getLogger(Client.class.getName());

    public static void main(String[] args) throws Exception {
        final Options options = new Options();
        final Option testMode = Option.builder().longOpt("testMode").build();
        options.addOption(testMode);

        final Option userRole = Option.builder().longOpt("userRole").hasArg().build();
        options.addOption(userRole);

        final CommandLineParser parser = new DefaultParser();
        final CommandLine commandLine = parser.parse(options, args);

        boolean itestMode = false;
        if (commandLine.hasOption("testMode"))
            itestMode = true;

        RoleType roleType = RoleType.Instance.findRole(5);
        if (commandLine.hasOption("userRole")){
            int id = Integer.parseInt(commandLine.getOptionValue("userRole"));
            roleType = RoleType.Instance.findRole(id);
            System.out.println(roleType);
        }


        logger.info("Client is running (testMode = {}, under a {}", itestMode , roleType);

    }
}
