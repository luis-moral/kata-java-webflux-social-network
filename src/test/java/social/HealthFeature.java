package social;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import social.infrastructure.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class HealthFeature {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void
    should_pass_if_server_working() {
        webTestClient
            .get()
                .uri("/health")
            .exchange()
                .expectStatus()
                    .isEqualTo(HttpStatus.OK)
                .expectBody(String.class)
                    .isEqualTo("OK");
    }
}
