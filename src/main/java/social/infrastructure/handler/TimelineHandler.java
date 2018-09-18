package social.infrastructure.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;
import social.application.TimelineApi;

public class TimelineHandler {

    private static final String PATH_VARIABLE_USER = "user";

    private final TimelineApi timelineApi;

    public TimelineHandler(TimelineApi timelineApi) {
        this.timelineApi = timelineApi;
    }

    public Mono<ServerResponse> postMessage(ServerRequest request) {
        return
            request
                .bodyToMono(String.class)
                .map(body -> Tuples.of(request.pathVariable(PATH_VARIABLE_USER), body))
                .doOnNext(tuple -> timelineApi.postMessageFor(tuple.getT1(), tuple.getT2()))
                .then(
                    ServerResponse
                        .status(HttpStatus.CREATED)
                        .build()
                );
    }
}
