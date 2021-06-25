package edu.episen.si.ing1.pds.backend.server.network.exchange;

import edu.episen.si.ing1.pds.backend.server.utils.aes.AESUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Sender {
    private final SocketParams params;

    public Sender(SocketParams params) {
        this.params = params;
    }

    public void println(String msg) throws Exception {
        boolean encrypted = params.isEncrypted();
        if(encrypted)
            msg = AESUtils.encrypt(msg);

        System.out.println("msg to be sent: " + msg);
        SelectionKey key = params.getKey();
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        channel.write(buffer);
        buffer.clear();
    }
}
