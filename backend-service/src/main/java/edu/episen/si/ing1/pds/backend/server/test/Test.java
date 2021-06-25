package edu.episen.si.ing1.pds.backend.server.test;

import edu.episen.si.ing1.pds.backend.server.network.server.Server;
import edu.episen.si.ing1.pds.backend.server.utils.aes.AESUtils;
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
                .thread(2)
                .serve();
    }
}
