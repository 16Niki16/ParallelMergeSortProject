package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static final int SERVER_PORT = 7777;
    private static final String SERVER_HOST = "localhost";
    private static final int BUFFER_SIZE = 512;
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
            }
        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the network communication", e);
        }
    }

    private String serverOutput(SocketChannel sc) throws IOException {
        buffer.clear();
        sc.read(buffer);
        buffer.flip();

        byte[] byteArray = new byte[buffer.remaining()];
        buffer.get(byteArray);
        return new String(byteArray, StandardCharsets.UTF_8);
    }

    private void clientInput(SocketChannel sc) throws IOException {
        buffer.clear();
        buffer.put(message.getBytes());
        buffer.flip();
        sc.write(buffer);
    }

    private boolean disconnect(String command) {
        return command.equals("disconnect");
    }

}

