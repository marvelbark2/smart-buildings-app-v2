package edu.episen.si.ing1.pds.backend.server.network.exchange;

import edu.episen.si.ing1.pds.backend.server.utils.aes.AESUtils;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class Receiver {
    private final SocketParams params;

    public Receiver(SocketParams params) {
        this.params = params;
    }

    public String nextLine() throws Exception {
        SelectionKey key = params.getKey();
        if(key.isReadable()) {
            SocketChannel channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int numRead = -1;
            numRead = channel.read(buffer);

            if (numRead == -1) {
                Socket socket = channel.socket();
                SocketAddress remoteAddr = socket.getRemoteSocketAddress();
                System.out.println("Connection closed by client: " + remoteAddr);
                channel.close();
                key.cancel();
                params.setClosed(true);
                return null;
            }

            byte[] data = new byte[numRead];
            System.arraycopy(buffer.array(), 0, data, 0, numRead);
            boolean encrypted = params.isEncrypted();
            String received = new String(data);
            if(!encrypted)
                return  received;
            else
                return AESUtils.decrypt(received);
        } else {
            return null;
        }

    }
}
