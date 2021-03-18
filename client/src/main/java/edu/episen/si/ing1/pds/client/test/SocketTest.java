package edu.episen.si.ing1.pds.client.test;

import edu.episen.si.ing1.pds.client.network.SocketClient;
import edu.episen.si.ing1.pds.client.network.SocketConfig;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class SocketTest {

    public void testSockets() {
        Map<String, String> values = new HashMap();
        values.put("name", "Eric");
        values.put("email", "eric@u.fr");
        values.put("telephone", "0888288331");
        CompletableFuture.allOf(
                futureList().stream()
                        .map(future -> future.thenAccept(socket -> {
                                    SocketClient client = new SocketClient(socket);
                                    //welcome message
                                    client.readMessage();

                                    client.create(values);
                                    client.update(values);
                                    client.delete(values);
                                    client.read(values);

                                    client.close();
                                }
                        )).toArray(CompletableFuture[]::new)
        );
    }

    private List<CompletableFuture<Socket>> futureList() {
        List<CompletableFuture<Socket>> futures = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            futures.add(socketFactory());
        }
        return futures;
    }

    private CompletableFuture<Socket> socketFactory() {
        Socket socket = null;
        try {
            SocketConfig config = SocketConfig.Instance;
            InetAddress host = InetAddress.getByName(config.HOST);
            socket = new Socket(host, config.PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Socket finalSocket = socket;
        return CompletableFuture.supplyAsync(() -> finalSocket);
    }

}

