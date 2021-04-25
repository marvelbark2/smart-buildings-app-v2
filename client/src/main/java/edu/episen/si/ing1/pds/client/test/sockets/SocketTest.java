package edu.episen.si.ing1.pds.client.test.sockets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import edu.episen.si.ing1.pds.client.network.SocketClient;
import edu.episen.si.ing1.pds.client.network.SocketConfig;
import edu.episen.si.ing1.pds.client.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SocketTest {

    public void testSockets(int nSimulation) {
        Object values = sampleData();
        CompletableFuture.allOf(
                futureList(nSimulation).stream()
                        .map(future -> future.thenAccept(socket -> {
                                    SocketClient client = new SocketClient(socket);
                                    client.create(values);
                                    client.update(values);
                                    client.delete(values);
                                    client.read(values);

                                    client.close();
                                }
                        )).toArray(CompletableFuture[]::new)
        );
    }

    private List<CompletableFuture<Socket>> futureList(int nSimulation) {
        List<CompletableFuture<Socket>> futures = new ArrayList<>();
        for (int i = 0; i < nSimulation; i++) {
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

    private Object sampleData() {
        File data = Utils.getFileContent("SMARTBUILDSAMPLE");
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode node = null;
        try {
            node = (ArrayNode) mapper.readTree(data).get("data");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return node;
    }
}

