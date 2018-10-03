package social.infrastructure.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import social.domain.UserMessage;

import java.util.concurrent.TimeUnit;

public class MessageRepositoryShould {

    private static final long NOW = System.currentTimeMillis();
    private static final String BOB = "Bob";
    private static final UserMessage BOB_MESSAGE = new UserMessage(BOB, "Hello World!", NOW - TimeUnit.SECONDS.toMillis(5));
    private static final UserMessage ANOTHER_BOB_MESSAGE = new UserMessage(BOB, "Hello World again!", NOW - TimeUnit.SECONDS.toMillis(1));

    @Test public void
    save_user_messages() {
        MessageRepository messageRepository = new MessageRepository();
        messageRepository.saveMessageFor(BOB, BOB_MESSAGE);
        messageRepository.saveMessageFor(BOB, ANOTHER_BOB_MESSAGE);

        Assertions
            .assertThat(messageRepository.findMessagesFor(BOB))
            .contains(BOB_MESSAGE, ANOTHER_BOB_MESSAGE);
    }

    @Test
    public void
    find_user_messages_in_chronological_order() {
        MessageRepository messageRepository = new MessageRepository();
        messageRepository.saveMessageFor(BOB, BOB_MESSAGE);
        messageRepository.saveMessageFor(BOB, ANOTHER_BOB_MESSAGE);

        Assertions
            .assertThat(messageRepository.findMessagesFor(BOB))
            .containsSequence(ANOTHER_BOB_MESSAGE, BOB_MESSAGE);
    }
}
