package edu.episen.si.ing1.pds.backend.server.network.server;

import edu.episen.si.ing1.pds.backend.server.network.config.SocketConfig;
import edu.episen.si.ing1.pds.backend.server.network.exchange.nio.Receiver;
import edu.episen.si.ing1.pds.backend.server.network.exchange.nio.Sender;
import edu.episen.si.ing1.pds.backend.server.network.exchange.nio.SocketExchange;
import edu.episen.si.ing1.pds.backend.server.network.exchange.socket.SocketHandler;
import edu.episen.si.ing1.pds.backend.server.network.exchange.socket.SocketParams;
import edu.episen.si.ing1.pds.backend.server.pool.DataSource;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class SocketServer {
    private Selector selector;
    private InetSocketAddress listenAddress;
    private final boolean encrypted;
    private DataSource ds;

    public SocketServer(boolean encrypted) throws IOException {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        int port = SocketConfig.Instance.PORT;
        this.encrypted = encrypted;
        listenAddress = new InetSocketAddress(address, port);
    }
    // create server channel
    public void startServer(SocketHandler handler) throws Exception {
        this.selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);

        // retrieve server socket and bind to port
        serverChannel.socket().bind(listenAddress);
        serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);

        System.out.println("Server started...");

        while (true) {
            // wait for events
            this.selector.select();
            //work on selected keys
            Iterator keys = this.selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = (SelectionKey) keys.next();
                System.out.println("keys: " + key);
                if (key.isAcceptable()) {
                    this.accept(key);
                    // new ciient !!
                    System.out.println("Ready for use");
                    SocketParams params = new SocketParams(key, encrypted);
                    System.out.println(params.hashCode());
                    Receiver receiver = new Receiver(params);
                    Sender sender = new Sender(params);

                    SocketExchange exchange = new SocketExchange(receiver, sender);
                    exchange.setClosed(params.isClosed());
                    handler.handle(exchange);
                    if(!params.isClosed())
                        sender.println("end\n");
                    else {
                        System.out.println("Disconnected");
                    }
                } else if(!key.isValid())
                    System.out.println("user disconnected");
                keys.remove();
            }
        }
    }

    //accept a connection made to this channel's socket
    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverChannel.accept();
        channel.configureBlocking(false);
        Socket socket = channel.socket();
        SocketAddress remoteAddr = socket.getRemoteSocketAddress();
        System.out.println("Connected to: " + remoteAddr);
        SocketParams.setConnection(ds.getConnectionPool().getConnection());
        channel.register(this.selector, SelectionKey.OP_READ);
    }

    public void setDataSource(DataSource ds) {
        this.ds = ds;
    }
}



