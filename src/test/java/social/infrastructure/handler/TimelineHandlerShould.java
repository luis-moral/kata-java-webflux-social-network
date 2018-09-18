package social.infrastructure.handler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import social.application.TimelineApi;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TimelineHandlerShould {

    private static final String BOB = "Bob";
    private static final String BOB_MESSAGE = "Hello World!";

    @MockBean
    TimelineApi timelineApi;

    @Autowired
    private WebTestClient webTestClient;

    @Test public void
    allow_users_to_post_messages() {
        webTestClient
            .post()
                .uri("/api/" + BOB + "/timeline")
                .syncBody(BOB_MESSAGE)
            .exchange()
                .expectStatus()
                    .isEqualTo(HttpStatus.CREATED);

        Mockito
            .verify(timelineApi, Mockito.times(1))
            .postMessageFor(BOB, BOB_MESSAGE);
    }
}
