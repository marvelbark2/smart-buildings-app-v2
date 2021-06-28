package edu.episen.si.ing1.pds.backend.server.network.exchange.nio;

import edu.episen.si.ing1.pds.backend.server.network.exchange.socket.SocketParams;
import edu.episen.si.ing1.pds.backend.server.utils.aes.AESUtils;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class Sender {
    private final SocketParams params;

    public Sender(SocketParams params) {
        this.params = params;
    }

    public void println(String msg) throws Exception {
        if(msg != null) {
            msg += "\n";
            boolean encrypted = params.isEncrypted();
            if(encrypted)
                msg = AESUtils.encrypt(msg);
            SelectionKey key = params.getKey();
            System.out.println("msg to be sent: " + msg);
            SocketChannel channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
            channel.write(buffer);
            buffer.clear();
        }
    }
}
