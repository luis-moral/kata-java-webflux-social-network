package social.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import social.infrastructure.repository.MessageRepository;

@RunWith(MockitoJUnitRunner.class)
public class TimelineApiShould {

    private static final String BOB = "Bob";
    private static final String BOB_MESSAGE = "Hello World!";

    @Mock
    MessageRepository messageRepository;

    @Test public void
    allow_users_to_post_messages() {
        BDDMockito
            .willDoNothing()
            .given(messageRepository)
            .postMessageFor(BOB, BOB_MESSAGE);

        TimelineApi timelineApi = new TimelineApi(messageRepository);
        timelineApi.postMessageFor(BOB, BOB_MESSAGE);

        Mockito
            .verify(messageRepository, Mockito.times(1))
            .postMessageFor(BOB, BOB_MESSAGE);
    }
}
