package main.java.persistance.sockets;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class SocketService {
    private Selector selector;
    private Map<SocketChannel,List> dataMapper;
    private final InetSocketAddress listenAddress;

    public SocketService(String address, int port) throws IOException {
        listenAddress = new InetSocketAddress(address, port);
        dataMapper = new HashMap<>();
    }

    // create Server channel	
    public void startServer() throws IOException {
        this.selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);

        // retrieve Server socket and bind to port
        ServerSocket serverSocket = serverChannel.socket();
        serverSocket.bind(listenAddress);
        serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);

        //System.out.println("Server started...");

        while (true) {
            // wait for events
            this.selector.select();

            //work on selected keys
            Iterator keys = this.selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = (SelectionKey) keys.next();

                // this is necessary to prevent the same key from coming up 
                // again the next time around.
                keys.remove();

                if (!key.isValid()) {
                    close(serverChannel, key, serverSocket);
                    return;

                } else if (key.isAcceptable()) {
                    this.accept(key);

                } else if (key.isReadable()) {
                    this.read(key);

                } else {
                    close(serverChannel, key, serverSocket);
                    return;
                }
            }
        }
    }

    //accept a connection made to this channel's socket
    public void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverChannel.accept();
        channel.configureBlocking(false);
        Socket socket = channel.socket();
        SocketAddress remoteAddr = socket.getRemoteSocketAddress();
        System.out.println("Connected to: " + remoteAddr);

        // register channel with selector for further IO
        dataMapper.put(channel, new ArrayList());
        channel.register(this.selector, SelectionKey.OP_READ);
    }

    //read from the socket channel
    public void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 6);
        int numRead = -1;
        numRead = channel.read(buffer);

        if (numRead == -1) {
            close(this, channel, key, buffer);
            return;
        }

        byte[] data = new byte[numRead];
        System.arraycopy(buffer.array(), 0, data, 0, numRead);
        //rewrite String.
        String text = new String(data, "UTF-8"); //Arrays.toString(data);

        if ("Bue.".equals(text.trim())) {
            close(this, channel, key, buffer);
            return;
        }

        ByteBuffer output = ByteBuffer.wrap(data);
        System.out.print(text);
        channel.write(output);
    }

    public void close(SocketService service, SocketChannel channel, SelectionKey key, ByteBuffer buffer) throws IOException {
        this.dataMapper.remove(channel);
        Socket socket = channel.socket();
        SocketAddress remoteAddr = socket.getRemoteSocketAddress();
        System.out.println("Connection closed by client: " + remoteAddr);
        key.cancel();
        socket.close();
        buffer.clear();
        channel.close();

    }

    public void close(ServerSocketChannel serverChannel, SelectionKey key, ServerSocket serverSocket) throws IOException {
        key.cancel();
        serverChannel.close();
        serverSocket.close();
        this.selector.close();
    }
}