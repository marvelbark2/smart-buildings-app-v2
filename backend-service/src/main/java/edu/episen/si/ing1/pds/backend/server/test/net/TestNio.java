package edu.episen.si.ing1.pds.backend.server.test.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class TestNio {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocket = null;
        SocketChannel client = null;
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(9000));
        client = serverSocket.accept();

        // new client
        System.out.println("Connection Set:  " + client.getRemoteAddress());

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while(client.read(buffer) > 0) {
            buffer.flip();
            String msg = "Received " + new String(buffer.array());
            client.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
            buffer.clear();
        }
        System.out.println("File Received");
        client.close();
    }
}
