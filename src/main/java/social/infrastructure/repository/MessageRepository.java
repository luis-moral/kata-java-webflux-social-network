package social.infrastructure.repository;

import social.domain.UserMessage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MessageRepository {

    private final Map<String, List<UserMessage>> messageByUserMap;

    public MessageRepository() {
        messageByUserMap = new HashMap<>();
    }

    public List<UserMessage> findMessagesFor(String user) {
        return messageByUserMap.get(user);
    }

    public void saveMessageFor(String user, UserMessage message) {
        messageByUserMap
            .computeIfAbsent(user, key -> new LinkedList<>())
            .add(message);
    }

    public void reset() {
        messageByUserMap.clear();
    }
}
