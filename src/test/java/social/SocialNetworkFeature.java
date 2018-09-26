package social;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import social.infrastructure.Application;
import social.infrastructure.repository.MessageRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class SocialNetworkFeature {

    private static final String BOB = "Bob";
    private static final String BOB_MESSAGE_TEXT = "Hello World!";

    private static final String CHARLIE = "Charlie";
    private static final String CHARLIE_MESSAGE_TEXT = "Hello!";
    private static final String CHARLIE_MESSAGE_TIME_FORMATTED = "(1 minute ago)";
    private static final String ANOTHER_CHARLIE_MESSAGE = "Nice to meet you";
    private static final String ANOTHER_CHARLIE_MESSAGE_TIME_FORMATTED = "(2 minutes ago)";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private MessageRepository messageRepository;

    @Test public void
    users_can_write_messages_to_a_timeline() {
        webTestClient
            .post()
                .uri("/api/" + BOB + "/timeline")
                .syncBody(BOB_MESSAGE_TEXT)
            .exchange()
                .expectStatus()
                    .isEqualTo(HttpStatus.CREATED);

        Assertions
            .assertThat(messageRepository.findMessagesFor(BOB))
            .hasSize(1);
        Assertions
            .assertThat(messageRepository.findMessagesFor(BOB).get(0).getText())
            .isEqualTo(BOB_MESSAGE_TEXT);
    }

    @Test public void
    users_can_read_user_messages() {
        webTestClient
            .get()
                .uri("/api/" + CHARLIE + "/timeline")
            .exchange()
                .expectStatus()
                    .isEqualTo(HttpStatus.CREATED)
                .expectBody(String.class)
                    .isEqualTo(
                        CHARLIE_MESSAGE_TEXT + " " + CHARLIE_MESSAGE_TIME_FORMATTED + "\n" +
                        ANOTHER_CHARLIE_MESSAGE + " " + ANOTHER_CHARLIE_MESSAGE_TIME_FORMATTED
                    );
    }
}
