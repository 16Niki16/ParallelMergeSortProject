package client;

import constants.Numbers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client implements ClientAPI {
    public static final int SERVER_PORT = 7775;
    private static final String SERVER_HOST = "localhost";
    private static final int BUFFER_SIZE = Numbers.ONE_MILLION;
    private static final ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
    private final StringBuilder message;
    public Client() {
        this.message = new StringBuilder();
    }

    @Override
    public void serverConnect() {
        try (SocketChannel socketChannel = SocketChannel.open();
             Scanner scanner = new Scanner(System.in)) {
            socketChannel.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
            while (true) {
                System.out.println("""   
                    Enter one of the commands:
                       - Array with numbers divided by ','
                       - Number of threads: <numbers of threads> Array: <Array with numbers divided by ','>
                       - Get exceptions
                       - Disconnect""");
                String line = scanner.nextLine();
                message.append(line);
                if (disconnect(line)) {
                    break;
                }
                clientInput(socketChannel);
                serverOutput(socketChannel);
                System.out.println(this.message);
                this.message.setLength(Numbers.ZERO);
            }
        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the network communication", e);
        }
    }

    private void serverOutput(SocketChannel sc) throws IOException {
        message.setLength(Numbers.ZERO);
        while (true) {
            buffer.clear();
            sc.read(buffer);

            buffer.flip();
            byte[] byteArray = new byte[buffer.remaining()];
            buffer.get(byteArray);

            String chunk = new String(byteArray, StandardCharsets.UTF_8).strip();
            if (chunk.isEmpty() || chunk.equals("END")) {
                break;
            } else if ("END".equals(chunk.substring(chunk.length() - Numbers.THREE))) {
                message.append(chunk, Numbers.ZERO, chunk.length() - Numbers.THREE);
                break;
            }

            message.append(chunk);
        }
    }

    private void clientInput(SocketChannel sc) throws IOException {
        int chunkSize = Numbers.MAX_CHUNK_SIZE;
        int totalLength = message.length();
        int start = Numbers.ZERO;

        while (start < totalLength) {
            int end = Math.min(start + chunkSize, totalLength);
            String chunk = message.substring(start, end);

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

    private boolean disconnect(String command) {
        return command.equalsIgnoreCase("disconnect");
    }
}