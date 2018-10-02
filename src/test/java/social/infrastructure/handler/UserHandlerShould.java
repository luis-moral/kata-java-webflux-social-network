package social.infrastructure.handler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import social.application.UserApi;
import social.application.collaborator.Clock;
import social.domain.UserMessage;
import social.infrastructure.collaborator.MessageFormatter;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserHandlerShould {

    private static final String BOB = "Bob";
    private static final String BOB_MESSAGE_TEXT = "Hello World!";
    private static final long BOB_MESSAGE_TIME = System.currentTimeMillis();
    private static final UserMessage BOB_MESSAGE = new UserMessage(BOB_MESSAGE_TEXT, BOB_MESSAGE_TIME);
    private static final String CHARLIE = "Charlie";

    @MockBean
    Clock clock;

    @MockBean
    MessageFormatter messageFormatter;

    @MockBean
    UserApi userApi;

    @Autowired
    private WebTestClient webTestClient;

    @Test public void
    allow_users_to_post_messages() {
        webTestClient
            .post()
                .uri("/api/" + BOB + "/timeline")
                .syncBody(BOB_MESSAGE_TEXT)
            .exchange()
                .expectStatus()
                    .isEqualTo(HttpStatus.CREATED);

        Mockito
            .verify(userApi, Mockito.times(1))
            .postMessageFor(BOB, BOB_MESSAGE_TEXT);
    }

    @Test public void
    allow_users_to_read_other_users_messages_timeline() {
        long now = System.currentTimeMillis();

        BDDMockito
            .given(clock.currentTime())
            .willReturn(now);

        BDDMockito
            .given(userApi.getMessagesFor(BOB))
            .willReturn(Arrays.asList(BOB_MESSAGE));

        BDDMockito
            .given(messageFormatter.formatForRead(BOB_MESSAGE, now))
            .willReturn(BOB_MESSAGE_TEXT);

        webTestClient
            .get()
                .uri("/api/" + BOB + "/timeline")
            .exchange()
                .expectStatus()
                    .isEqualTo(HttpStatus.OK);

        Mockito
            .verify(messageFormatter, Mockito.times(1))
            .formatForRead(BOB_MESSAGE, now);
    }

    @Test public void
    allow_users_to_follow_other_users() {
        webTestClient
            .post()
                .uri("/api/" + BOB + "/follow")
                .syncBody(CHARLIE)
            .exchange()
                .expectStatus()
                    .isEqualTo(HttpStatus.CREATED);

        Mockito
            .verify(userApi, Mockito.times(1))
            .followUser(BOB, CHARLIE);
    }
}
