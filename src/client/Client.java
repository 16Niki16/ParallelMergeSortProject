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
    private String message;


    public Client() {
        this.message = "";
    }

    @Override
    public void serverConnect() {
        try (SocketChannel socketChannel = SocketChannel.open();
             Scanner scanner = new Scanner(System.in)) {
            socketChannel.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
            while (true) {
                System.out.println("Enter array with numbers divided by ',' ");
                message = scanner.nextLine();
                if (disconnect(message)) {
                    break;
                }
                clientInput(socketChannel);
                String reply = serverOutput(socketChannel);
                System.out.println(reply);
            }
        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the network communication", e);
        }
    }

    private String serverOutput(SocketChannel sc) throws IOException {
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
            if (chunk.isEmpty()) {
                break;
            } else if ("END".equals(chunk.substring(chunk.length() - Numbers.THREE))) {
                messageBuilder.append(chunk, Numbers.ZERO, chunk.length() - Numbers.THREE);
                break;
            }

            messageBuilder.append(chunk);
        }
        return messageBuilder.toString();
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
        return command.equals("disconnect");
    }
}