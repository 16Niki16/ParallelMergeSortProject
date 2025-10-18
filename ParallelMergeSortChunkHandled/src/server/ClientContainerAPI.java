package server;

import java.util.Map;

public interface ClientContainerAPI {
    String getClientId();

    Map.Entry<Integer, String> getClientInput(String input);
    String getMessageByID(int id);
}
