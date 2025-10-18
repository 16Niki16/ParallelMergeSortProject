package server;

import constants.Numbers;

import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientContainer implements ClientContainerAPI {
    private static final String PARTIAL_MESSAGE = "There are more messages to get!";
    private static final String MESSAGE_AVAILABLE = "The full message is available!";
    private static AtomicInteger clientId = new AtomicInteger(0);
    private ConcurrentHashMap<Integer, StringBuilder> array;

    public ClientContainer() {
        this.array = new ConcurrentHashMap<>();
    }

    public String getClientId() {
        int clientCreate = clientId.incrementAndGet();
        this.array.put(clientCreate, new StringBuilder());
        return "client " + clientCreate + "number";
    }

    public Map.Entry<Integer, String> getClientInput(String input) {
        Pattern pattern = Pattern.compile("client\\s*(.*?)\\s*number");
        Matcher matcher = pattern.matcher(input);
        int client = 0;

        if (matcher.find()) {
            String group = matcher.group(1);
            client = Integer.parseInt(group);
        }
        System.out.println("client "+ client);

        StringBuilder buildMessage = array.get(client);
        String[] messagesWithClients = input.split("\\n");
        System.out.println("Length: " + messagesWithClients.length);

        for (String messagesWithClient : messagesWithClients) {
            int clientIndex = messagesWithClient.indexOf("number");
            if (clientIndex == -1){
                if(messagesWithClient.contains("END")){
                    String message = messagesWithClient.substring(0, messagesWithClient.length() - 3).strip();
                    buildMessage.append(message);
                    return new AbstractMap.SimpleEntry<>(client, MESSAGE_AVAILABLE);
                }
                buildMessage.append(messagesWithClient);
            }
            String message = messagesWithClient.substring(clientIndex + "number".length()).strip();

            if (message.contains("END")) {

                buildMessage.append(message.substring(0, message.length() - 3).strip());
                return new AbstractMap.SimpleEntry<>(client, MESSAGE_AVAILABLE);
            }
            buildMessage.append(message);
        }
        return new AbstractMap.SimpleEntry<>(client, PARTIAL_MESSAGE);
    }

    @Override
    public String getMessageByID(int id) {
        String message = this.array.get(id).toString();
        this.array.get(id).setLength(0);
        return message;
    }

}
