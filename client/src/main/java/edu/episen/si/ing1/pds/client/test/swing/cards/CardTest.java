package edu.episen.si.ing1.pds.client.test.swing.cards;

import edu.episen.si.ing1.pds.client.network.SocketConfig;

import javax.swing.*;


public class CardTest {
    public static void main(String[] args) {
        SocketConfig.Instance.setEnv(true);

        SwingUtilities.invokeLater(ContextFrame::new);
    }
}
