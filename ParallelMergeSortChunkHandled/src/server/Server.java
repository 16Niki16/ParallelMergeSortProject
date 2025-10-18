package server;

import constants.Numbers;
import response.OutputManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class Server implements ServerAPI {
    private static final int SERVER_PORT = 7775;
    private static final String SERVER_HOST = "localhost";
    private static final int BUFFER_SIZE = Numbers.ONE_MILLION;
    private static final String MESSAGE_AVAILABLE = "The full message is available!";
    private ClientContainerAPI clientContainer;
    private ByteBuffer buffer;

    public Server() {
        this.clientContainer = new ClientContainer();
        this.buffer = ByteBuffer.allocate(BUFFER_SIZE);
    }

    @Override
    public void serverStart() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
             ForkJoinPool pool = ForkJoinPool.commonPool()) {

            serverSocketChannel.bind(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
            serverSocketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


            while (true) {
                int readyChannels = selector.select();

                if (readyChannels == Numbers.ZERO) {
                    continue;
                }

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();

                    if (key.isReadable()) {
                        try {
                            SocketChannel sc = (SocketChannel) key.channel();
                            readable(sc, pool);
                        } catch (IOException | NullPointerException e) {

                            continue;
                        }
                    } else if (key.isAcceptable()) {
                        acceptable(key, selector);
                    }
                    keyIterator.remove();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the server socket", e);
        }
    }

    private void readable(SocketChannel sc, ForkJoinPool pool) throws IOException {
        Map.Entry<Integer,String> line = clientInput(sc);
        assert line != null;
        if (line.getValue().equals(MESSAGE_AVAILABLE)) {
            clientOutput(sc, OutputManager.outputManager(clientContainer.getMessageByID(line.getKey()), pool));
        }
    }

    private void clientOutput(SocketChannel sc, String message) throws IOException {
        int chunkSize = Numbers.MAX_CHUNK_SIZE;
        int totalLength = message.length();
        int start = Numbers.ZERO;

        while (start < totalLength) {
            int end = Math.min(start + chunkSize, totalLength);
            String chunk = message.substring(start, end);
            System.out.println(chunk);
            chunkSending(sc, chunk);

            start = end;
        }
        chunkSending(sc, "END");
    }

    private void chunkSending(SocketChannel sc, String chunk) throws IOException {
        buffer.clear();
        buffer.put(chunk.getBytes());
        buffer.flip();
        sc.write(buffer);
    }

    private Map.Entry<Integer, String> clientInput(SocketChannel sc) throws IOException {
        buffer.clear();
        int bytesRead = sc.read(buffer);
        if (bytesRead < Numbers.ZERO) {
            System.out.println("Client has closed the connection!");
            sc.close();
            return null;
        }

        buffer.flip();
        byte[] byteArray = new byte[buffer.remaining()];
        buffer.get(byteArray);

        String chunk = new String(byteArray, StandardCharsets.UTF_8).strip();
        return clientContainer.getClientInput(chunk);
    }


    private void acceptable(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel sockChannel = (ServerSocketChannel) key.channel();
        SocketChannel accept = sockChannel.accept();
        accept.configureBlocking(false);
        accept.register(selector, SelectionKey.OP_READ);
        chunkSending(accept, clientContainer.getClientId());
    }
}