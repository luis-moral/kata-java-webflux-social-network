package social.application;

import social.application.collaborator.Clock;
import social.domain.UserMessage;
import social.infrastructure.repository.MessageRepository;
import social.infrastructure.repository.UserRepository;

import java.util.List;

public class UserApi {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final Clock clock;

    public UserApi(UserRepository userRepository, MessageRepository messageRepository, Clock clock) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.clock = clock;
    }

    public void postMessageFor(String user, String text) {
        messageRepository.saveMessageFor(user, new UserMessage(text, clock.currentTime()));
    }

    public List<UserMessage> getMessagesFor(String user) {
        return messageRepository.findMessagesFor(user);
    }

    public void followUser(String user, String userToFollow) {
        userRepository.saveUserToFollow(user, userToFollow);
    }
}
