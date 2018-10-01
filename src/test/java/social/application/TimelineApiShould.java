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

@RunWith(MockitoJUnitRunner.class)
public class TimelineApiShould {

    private static final long MESSAGE_TIME = System.currentTimeMillis();
    private static final String BOB = "Bob";
    private static final String BOB_MESSAGE_TEXT = "Hello World!";

    private static final UserMessage BOB_MESSAGE = new UserMessage(BOB_MESSAGE_TEXT, MESSAGE_TIME);

    @Mock
    MessageRepository messageRepository;
    @Mock
    Clock clock;

    private TimelineApi timelineApi;

    @Before
    public void setUp() {
        timelineApi = new TimelineApi(messageRepository, clock);
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

        timelineApi.postMessageFor(BOB, BOB_MESSAGE_TEXT);

        Mockito
            .verify(messageRepository, Mockito.times(1))
            .saveMessageFor(BOB, BOB_MESSAGE);
    }

    @Test public void
    allow_users_to_read_other_users_messages_timeline() {
        timelineApi.getMessagesFor(BOB);

        Mockito
            .verify(messageRepository, Mockito.times(1))
            .findMessagesFor(BOB);
    }
}
