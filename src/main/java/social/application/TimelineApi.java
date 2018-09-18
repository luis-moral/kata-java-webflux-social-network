package social.application;

import social.infrastructure.repository.MessageRepository;

public class TimelineApi {

    private final MessageRepository messageRepository;

    public TimelineApi(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void postMessageFor(String user, String message) {
        messageRepository.postMessageFor(user, message);
    }
}
