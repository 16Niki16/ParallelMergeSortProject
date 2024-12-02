package client;

import constants.Numbers;
import transform.ClientTransform;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static final int SERVER_PORT = 7777;
    private static final String SERVER_HOST = "localhost";
    private static final int BUFFER_SIZE = Numbers.TWO_HUNDRED_THOUSANDS;
    private static final ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
    private String message;


    public Client() {
        this.message = "";
    }

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
                assert reply != null;
                System.out.println(reply.length());
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
            if (bytesRead < 0) {
                System.out.println("Client has closed the connection!");
                sc.close();
                return null;
            }

            buffer.flip();
            byte[] byteArray = new byte[buffer.remaining()];
            buffer.get(byteArray);

            String chunk = new String(byteArray, StandardCharsets.UTF_8).strip();

            if ("END".equals(chunk)) {
                break;
            }else if ("END".equals(chunk.substring(chunk.length() - 3))) {
                messageBuilder.append(chunk, 0, chunk.length() - 3);
                break;
            }

            messageBuilder.append(chunk);
        }
        return messageBuilder.toString();
    }

    private void clientInput(SocketChannel sc) throws IOException {
        int chunkSize = 100_000;
        int totalLength = message.length();
        int start = 0;

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

