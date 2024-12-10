package server;

import constants.Numbers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class Server implements ServerAPI {
    private static final int SERVER_PORT = 7776;
    private static final String SERVER_HOST = "localhost";
    private static final int BUFFER_SIZE = Numbers.ONE_MILLION;

    @Override
    public void serverStart() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {

            serverSocketChannel.bind(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
            serverSocketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

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
                            readable(buffer, sc);
                        } catch (IOException e) {
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

    private void readable(ByteBuffer buffer, SocketChannel sc) throws IOException {
        String line = clientInput(buffer, sc);
        assert line != null;
        clientOutput(buffer, sc, OutputManager.outputManager(line));
    }

    private void clientOutput(ByteBuffer buffer, SocketChannel sc, String message) throws IOException {
        int chunkSize = Numbers.MAX_CHUNK_SIZE;
        int totalLength = message.length();
        int start = Numbers.ZERO;

        while (start < totalLength) {
            int end = Math.min(start + chunkSize, totalLength);
            String chunk = message.substring(start, end);
            chunkSending(buffer, sc, chunk);

            start = end;
        }
        chunkSending(buffer, sc, "END");
    }

    private void chunkSending(ByteBuffer buffer, SocketChannel sc, String chunk) throws IOException {
        buffer.clear();
        buffer.put(chunk.getBytes());
        buffer.flip();
        sc.write(buffer);
    }

    private String clientInput(ByteBuffer buffer, SocketChannel sc) throws IOException {
        StringBuilder messageBuilder = new StringBuilder();
        while (true) {
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
            System.out.println("chunk: " + chunk);
            if ("END".equals(chunk)) {
                break;
            } else if ("END".equals(chunk.substring(chunk.length() - Numbers.THREE))) {
                messageBuilder.append(chunk, Numbers.ZERO, chunk.length() - Numbers.THREE);
                break;
            }

            messageBuilder.append(chunk);
        }
        return messageBuilder.toString();
    }


    private void acceptable(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel sockChannel = (ServerSocketChannel) key.channel();
        SocketChannel accept = sockChannel.accept();
        accept.configureBlocking(false);
        accept.register(selector, SelectionKey.OP_READ);
    }
}