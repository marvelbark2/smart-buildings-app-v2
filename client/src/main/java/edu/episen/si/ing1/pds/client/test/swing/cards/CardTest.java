package edu.episen.si.ing1.pds.client.test.swing.cards;

import edu.episen.si.ing1.pds.client.network.SocketConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;


public class CardTest {
    static final Logger logger = LoggerFactory.getLogger(CardTest.class.getName());
    public static void main(String[] args) {
        logger.info("Gui service started");
        SocketConfig.Instance.setEnv(true);
        //SwingUtilities.invokeLater(ContextFrame::new);
    }
}
