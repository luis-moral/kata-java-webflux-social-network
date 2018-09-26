package social.infrastructure.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class HealthHandler {

    public Mono<ServerResponse> health(ServerRequest request) {
        return
            ServerResponse
                .status(HttpStatus.OK)
                .body(Mono.fromCallable(() -> "OK"), String.class);
    }
}
