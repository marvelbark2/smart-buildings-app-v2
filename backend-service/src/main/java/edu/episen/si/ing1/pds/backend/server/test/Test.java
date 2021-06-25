package edu.episen.si.ing1.pds.backend.server.test;

import edu.episen.si.ing1.pds.backend.server.network.server.Server;
import edu.episen.si.ing1.pds.backend.server.utils.Properties;
import edu.episen.si.ing1.pds.backend.server.utils.aes.AESUtils;
import edu.episen.si.ing1.pds.backend.server.workspace.TestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {
    static final Logger logger = LoggerFactory.getLogger(Test.class.getName());
    public static void main(String[] args) throws Exception {
        logger.info("Test started");
        Server.init()
                .testMode(false)
                .setConfigVar("toto")
                .setEncrypted(true)
                .setDs(6)
                .setHandler(new TestHandler())
                .thread(true)
                .serve();
        Properties.executor.shutdownNow().forEach(System.out::println);
    }
}
