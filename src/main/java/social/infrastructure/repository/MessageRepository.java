package social.infrastructure.repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MessageRepository {

    private final Map<String, List<String>> messageByUserMap;

    public MessageRepository() {
        messageByUserMap = new HashMap<>();
    }

    public List<String> messagesFor(String user) {
        return messageByUserMap.get(user);
    }

    public void saveMessageFor(String user, String message) {
        messageByUserMap
            .computeIfAbsent(user, k -> new LinkedList<>())
            .add(message);
    }
}
