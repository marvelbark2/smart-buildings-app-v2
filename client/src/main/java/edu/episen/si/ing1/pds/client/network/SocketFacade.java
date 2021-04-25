package edu.episen.si.ing1.pds.client.network;

import java.net.InetAddress;
import java.net.Socket;

public enum SocketFacade {
    Instance;
    private Socket socket;

    SocketFacade() {
        try {
            String host = SocketConfig.Instance.HOST;
            InetAddress address = InetAddress.getByName(host);
            socket = new Socket(address, SocketConfig.Instance.PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
