package social.infrastructure.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import social.domain.UserMessage;

public class MessageRepositoryShould {

    private static final String BOB = "Bob";
    private static final UserMessage BOB_MESSAGE = new UserMessage("Hello World!", System.currentTimeMillis());
    private static final UserMessage ANOTHER_BOB_MESSAGE = new UserMessage("Hello World again!", System.currentTimeMillis());

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
