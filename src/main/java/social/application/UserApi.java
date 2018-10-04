package social.application;

import social.application.collaborator.Clock;
import social.domain.UserMessage;
import social.infrastructure.repository.MessageRepository;
import social.infrastructure.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        messageRepository.saveMessageFor(user, new UserMessage(user, text, clock.currentTime()));
    }

    public List<UserMessage> messagesFor(String user) {
        return
            messageRepository
                .findMessagesFor(user)
                .stream()
                .sorted((o1, o2) -> Long.compare(o2.getTime(), o1.getTime()))
                .collect(Collectors.toList());
    }

    public void followUser(String user, String userToFollow) {
        userRepository.saveUserToFollow(user, userToFollow);
    }

    public List<UserMessage> wallFor(String user) {
        return
            Stream
                .concat(
                    messageRepository
                        .findMessagesFor(user)
                        .stream(),
                    userRepository
                        .usersFollowedBy(user)
                        .stream()
                        .flatMap(someUser -> messagesFor(someUser).stream())
                )
                .sorted((o1, o2) -> Long.compare(o2.getTime(), o1.getTime()))
                .collect(Collectors.toList());
    }
}
