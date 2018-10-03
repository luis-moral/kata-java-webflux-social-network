package social.application;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import social.application.collaborator.Clock;
import social.domain.UserMessage;
import social.infrastructure.repository.MessageRepository;
import social.infrastructure.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserApiShould {

    private static final long MESSAGE_TIME = System.currentTimeMillis();
    private static final String BOB = "Bob";
    private static final String BOB_MESSAGE_TEXT = "Hello World!";
    private static final String CHARLIE = "Charlie";

    private static final UserMessage BOB_MESSAGE = new UserMessage(BOB, BOB_MESSAGE_TEXT, MESSAGE_TIME);

    @Mock
    UserRepository userRepository;
    @Mock
    MessageRepository messageRepository;
    @Mock
    Clock clock;

    private UserApi userApi;

    @Before
    public void setUp() {
        userApi = new UserApi(userRepository, messageRepository, clock);
    }

    @Test public void
    allow_users_to_post_messages() {
        BDDMockito
            .willDoNothing()
            .given(messageRepository)
            .saveMessageFor(BOB, BOB_MESSAGE);

        BDDMockito
            .given(clock.currentTime())
            .willReturn(MESSAGE_TIME);

        userApi.postMessageFor(BOB, BOB_MESSAGE_TEXT);

        Mockito
            .verify(messageRepository, Mockito.times(1))
            .saveMessageFor(BOB, BOB_MESSAGE);
    }

    @Test public void
    allow_users_to_read_other_users_messages_timeline() {
        userApi.messagesFor(BOB);

        Mockito
            .verify(messageRepository, Mockito.times(1))
            .findMessagesFor(BOB);
    }

    @Test public void
    allow_users_to_follow_other_users() {
        userApi.followUser(BOB, CHARLIE);

        Mockito
            .verify(userRepository, Mockito.times(1))
            .saveUserToFollow(BOB, CHARLIE);
    }
}
