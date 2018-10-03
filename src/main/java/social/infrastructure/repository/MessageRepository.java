package social.infrastructure.repository;

import social.domain.UserMessage;

import java.util.*;
import java.util.stream.Collectors;

public class MessageRepository {

    private final Map<String, List<UserMessage>> messageByUserMap;

    public MessageRepository() {
        messageByUserMap = new HashMap<>();
    }

    public List<UserMessage> findMessagesFor(String user) {
        return
            messageByUserMap
                .get(user)
                .stream()
                .sorted((o1, o2) -> Long.compare(o2.getTime(), o1.getTime()))
                .collect(Collectors.toList());
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
