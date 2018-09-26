package social.application;

import social.application.collaborator.Clock;
import social.domain.UserMessage;
import social.infrastructure.repository.MessageRepository;

import java.util.List;

public class TimelineApi {

    private final MessageRepository messageRepository;
    private final Clock clock;

    public TimelineApi(MessageRepository messageRepository, Clock clock) {
        this.messageRepository = messageRepository;
        this.clock = clock;
    }

    public void postMessageFor(String user, String text) {
        messageRepository.saveMessageFor(user, new UserMessage(text, clock.currentTime()));
    }

    public List<UserMessage> getMessagesFor(String user) {
        return messageRepository.findMessagesFor(user);
    }
}
