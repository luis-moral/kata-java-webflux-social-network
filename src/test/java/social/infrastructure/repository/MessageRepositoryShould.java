package social.infrastructure.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class MessageRepositoryShould {

    private static final String BOB = "Bob";
    private static final String BOB_MESSAGE = "Hello World!";
    private static final String ANOTHER_BOB_MESSAGE = "Hello World again!";

    @Test public void
    save_user_messages() {
        MessageRepository messageRepository = new MessageRepository();
        messageRepository.saveMessageFor(BOB, BOB_MESSAGE);
        messageRepository.saveMessageFor(BOB, ANOTHER_BOB_MESSAGE);

        Assertions
            .assertThat(messageRepository.messagesFor(BOB))
            .containsExactly(BOB_MESSAGE, ANOTHER_BOB_MESSAGE);
    }
}
