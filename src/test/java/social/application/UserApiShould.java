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

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@RunWith(MockitoJUnitRunner.class)
public class UserApiShould {

    private static final long MESSAGE_TIME = System.currentTimeMillis();
    private static final String BOB = "Bob";
    private static final String BOB_MESSAGE_TEXT = "Hello World!";
    private static final UserMessage BOB_MESSAGE = new UserMessage(BOB, BOB_MESSAGE_TEXT, MESSAGE_TIME);

    private static final String CHARLIE = "Charlie";
    private static final String ALICE = "Alice";

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

    @Test public void
    allow_users_to_read_user_walls() {
        long now = System.currentTimeMillis();
        UserMessage firstMessage = new UserMessage(BOB, "Hi", now - TimeUnit.SECONDS.toMillis(5));
        UserMessage secondMessage = new UserMessage(CHARLIE, "Hi!", now - TimeUnit.SECONDS.toMillis(15));
        UserMessage thirdMessage = new UserMessage(BOB, "There!", now - TimeUnit.SECONDS.toMillis(25));
        UserMessage forthMessage = new UserMessage(ALICE, "Hello", now - TimeUnit.SECONDS.toMillis(35));

        BDDMockito
            .given(userRepository.usersFollowedBy(BOB))
            .willReturn(Arrays.asList(CHARLIE, ALICE));

        BDDMockito
            .given(messageRepository.findMessagesFor(BOB))
            .willReturn(Arrays.asList(firstMessage, thirdMessage));

        BDDMockito
            .given(messageRepository.findMessagesFor(CHARLIE))
            .willReturn(Arrays.asList(secondMessage));

        BDDMockito
            .given(messageRepository.findMessagesFor(ALICE))
            .willReturn(Arrays.asList(forthMessage));

        userApi.wallFor(BOB);

        Mockito
            .verify(messageRepository, Mockito.times(1))
            .findMessagesFor(BOB);

        Mockito
            .verify(messageRepository, Mockito.times(1))
            .findMessagesFor(CHARLIE);

        Mockito
            .verify(messageRepository, Mockito.times(1))
            .findMessagesFor(ALICE);
    }
}
