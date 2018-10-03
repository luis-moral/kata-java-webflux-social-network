package social;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import social.domain.UserMessage;
import social.infrastructure.Application;
import social.infrastructure.repository.MessageRepository;
import social.infrastructure.repository.UserRepository;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class SocialNetworkFeature {

    private static final long NOW = System.currentTimeMillis();

    private static final String BOB = "Bob";
    private static final String BOB_MESSAGE_TEXT = "Hello World!";
    private static final long BOB_MESSAGE_TIME = NOW - TimeUnit.MINUTES.toMillis(2);
    private static final String BOB_MESSAGE_TIME_FORMATTED = "(2 minutes ago)";

    private static final String CHARLIE = "Charlie";
    private static final String CHARLIE_MESSAGE_TEXT = "Hello!";
    private static final long CHARLIE_MESSAGE_TIME = NOW - TimeUnit.MINUTES.toMillis(1);
    private static final String CHARLIE_MESSAGE_TIME_FORMATTED = "(1 minute ago)";
    private static final String ANOTHER_CHARLIE_MESSAGE_TEXT = "Nice to meet you";
    private static final long ANOTHER_CHARLIE_MESSAGE_TIME = NOW - TimeUnit.MINUTES.toMillis(3);
    private static final String ANOTHER_CHARLIE_MESSAGE_TIME_FORMATTED = "(3 minutes ago)";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Before
    public void setUp() {
        userRepository.reset();
        messageRepository.reset();
    }

    @Test public void
    users_can_write_messages_to_a_timeline() {
        webTestClient
            .post()
                .uri("/api/" + CHARLIE + "/timeline")
                .syncBody(CHARLIE_MESSAGE_TEXT)
            .exchange()
                .expectStatus()
                    .isEqualTo(HttpStatus.CREATED);

        Assertions
            .assertThat(messageRepository.findMessagesFor(CHARLIE))
            .hasSize(1);
        Assertions
            .assertThat(messageRepository.findMessagesFor(CHARLIE).get(0).getText())
            .isEqualTo(CHARLIE_MESSAGE_TEXT);
    }

    @Test public void
    users_can_read_user_messages() {
        messageRepository.saveMessageFor(CHARLIE, new UserMessage(CHARLIE, CHARLIE_MESSAGE_TEXT, CHARLIE_MESSAGE_TIME));
        messageRepository.saveMessageFor(CHARLIE, new UserMessage(CHARLIE, ANOTHER_CHARLIE_MESSAGE_TEXT, ANOTHER_CHARLIE_MESSAGE_TIME));

        webTestClient
            .get()
                .uri("/api/" + CHARLIE + "/timeline")
            .exchange()
                .expectStatus()
                    .isEqualTo(HttpStatus.OK)
                .expectBody(String.class)
                    .consumeWith(response ->
                        Assertions
                            .assertThat(
                                    response.getResponseBody()
                            )
                            .isEqualTo(
                                CHARLIE_MESSAGE_TEXT + " " + CHARLIE_MESSAGE_TIME_FORMATTED + "\n" +
                                ANOTHER_CHARLIE_MESSAGE_TEXT + " " + ANOTHER_CHARLIE_MESSAGE_TIME_FORMATTED
                            )
                    );
    }

    @Test public void
    users_can_follow_other_users() {
        postMessage(BOB, BOB_MESSAGE_TEXT);

        webTestClient
            .post()
                .uri("/api/" + CHARLIE + "/follow")
                .syncBody(BOB)
            .exchange()
                .expectStatus()
                    .isEqualTo(HttpStatus.CREATED);
    }

    @Test public void
    users_can_read_users_walls() {
        messageRepository.saveMessageFor(CHARLIE, new UserMessage(CHARLIE, CHARLIE_MESSAGE_TEXT, CHARLIE_MESSAGE_TIME));
        messageRepository.saveMessageFor(BOB, new UserMessage(BOB, BOB_MESSAGE_TEXT, BOB_MESSAGE_TIME));
        messageRepository.saveMessageFor(CHARLIE, new UserMessage(CHARLIE, ANOTHER_CHARLIE_MESSAGE_TEXT, ANOTHER_CHARLIE_MESSAGE_TIME));

        userRepository.saveUserToFollow(CHARLIE, BOB);

        webTestClient
            .get()
                .uri("/api/" + CHARLIE + "/wall")
            .exchange()
                .expectStatus()
                    .isEqualTo(HttpStatus.OK)
                .expectBody(String.class)
                    .consumeWith(response ->
                        Assertions
                            .assertThat(response.getResponseBody())
                            .isEqualTo(
                                CHARLIE + " - " + CHARLIE_MESSAGE_TEXT + " " + CHARLIE_MESSAGE_TIME_FORMATTED + "\n" +
                                BOB + " - " + BOB_MESSAGE_TEXT + " " + BOB_MESSAGE_TIME_FORMATTED + "\n" +
                                CHARLIE + " - " + ANOTHER_CHARLIE_MESSAGE_TEXT + " " + ANOTHER_CHARLIE_MESSAGE_TIME_FORMATTED
                            )
                    );
    }

    public WebTestClient.ResponseSpec postMessage(String user, String text) {
        return
            webTestClient
                .post()
                    .uri("/api/" + user + "/timeline")
                    .syncBody(text)
                .exchange();
    }
}
