package social.infrastructure.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class HealthHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Mono<ServerResponse> health(ServerRequest request) {
        logger.info("Health check called");

        return
            ServerResponse
                .status(HttpStatus.OK)
                .body(Mono.fromCallable(() -> "OK"), String.class);
    }
}
