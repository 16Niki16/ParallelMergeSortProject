package client;

import constants.Numbers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client implements ClientAPI {
    private static final String PARTIAL_MESSAGE = "There are more messages to get!";
    public static final int SERVER_PORT = 7775;
    private static final String SERVER_HOST = "localhost";
    private static final int BUFFER_SIZE = Numbers.ONE_MILLION;
    private static final ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
    private String message;
    private String clientID;

    public Client() {
        this.message = "";
        this.clientID = "";
    }

    @Override
    public void serverConnect() {
        try (SocketChannel socketChannel = SocketChannel.open();
             Scanner scanner = new Scanner(System.in)) {
            socketChannel.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
            this.clientID = getClient(socketChannel);
            Thread.sleep(10);

            while (true) {
                System.out.println("""
                                                            
                    Enter one of the commands:
                     - Array with numbers divided by ','
                     - Number of threads: <numbers of threads> Array: <Array with numbers divided by ','>
                     - Get exceptions
                     - Disconnect
                     """);
                message = scanner.nextLine();
                if (disconnect(message)) {
                    break;
                }
                clientInput(socketChannel);
                String reply = serverOutput(socketChannel);
                assert reply != null;
                if (!reply.contains(PARTIAL_MESSAGE)) {
                    System.out.println(reply);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the network communication", e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String getClient(SocketChannel sc) throws IOException {
        buffer.clear();
        int bytesRead = sc.read(buffer);
        if (bytesRead < Numbers.ZERO) {
            sc.close();
            return null;
        }

        buffer.flip();
        byte[] byteArray = new byte[buffer.remaining()];
        buffer.get(byteArray);

        return new String(byteArray, StandardCharsets.UTF_8).strip();
    }

    private String serverOutput(SocketChannel sc) throws IOException {
        StringBuilder messageBuilder = new StringBuilder();
        while (true) {
            buffer.clear();
            int bytesRead = sc.read(buffer);
            if (bytesRead < Numbers.ZERO) {
                sc.close();
                return null;
            }

            buffer.flip();
            byte[] byteArray = new byte[buffer.remaining()];
            buffer.get(byteArray);

            String chunk = new String(byteArray, StandardCharsets.UTF_8).strip();

            if (chunk.contains("END")) {
                messageBuilder.append(chunk, 0, chunk.length() - 3);
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
            String chunk = this.clientID + " " + message.substring(start, end) + '\n';
            chunkSending(sc, chunk);
            start = end;
        }
        chunkSending(sc, this.clientID + " END");
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