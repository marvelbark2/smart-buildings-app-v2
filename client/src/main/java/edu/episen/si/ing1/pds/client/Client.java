package edu.episen.si.ing1.pds.client;

import edu.episen.si.ing1.pds.client.network.SocketConfig;
import edu.episen.si.ing1.pds.client.swing.global.Main;
import edu.episen.si.ing1.pds.client.utils.Utils;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class Client {
    private final static Logger logger = LoggerFactory.getLogger(Client.class.getName());

    private static void createAndShowGUI() {
        Main app = new Main();
        JFrame frame = app.getFrame();
       // Utils.checkSystemLog();
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("icon/logo.png")));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setContentPane(app.getGlobal());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width / 2 - frame.getWidth() / 2, screenSize.height / 2 - frame.getHeight() / 2);
        frame.pack();
    }

    public static void main(String[] args) throws Exception {
        final Options options = new Options();
        final Option testMode = Option.builder().longOpt("testMode").build();
        options.addOption(testMode);

        final Option userRole = Option.builder().longOpt("userRole").hasArg().build();
        options.addOption(userRole);

        final CommandLineParser parser = new DefaultParser();
        final CommandLine commandLine = parser.parse(options, args);

        boolean iTestMode = false;
        if (commandLine.hasOption("testMode"))
            iTestMode = true;

        SocketConfig.Instance.setEnv(iTestMode);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
        logger.info("Client is running (testMode = {}) ", iTestMode);

    }
}
