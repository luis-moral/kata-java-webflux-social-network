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
    private static final String BOB_MESSAGE = "Hello World!";

    @Autowired
    private WebTestClient webClient;

    @Test public void
    users_can_write_messages_to_a_timeline() {
        webClient
            .post()
                .uri("/api/" + BOB + "/timeline")
                .syncBody(BOB_MESSAGE)
            .exchange()
                .expectStatus()
                    .isEqualTo(HttpStatus.CREATED);

        MessageRepository messageRepository = new MessageRepository();
        Assertions.assertThat(messageRepository.messagesFor(BOB)).containsExactly(BOB_MESSAGE);
    }
}
