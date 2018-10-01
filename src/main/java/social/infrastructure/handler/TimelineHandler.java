package social.infrastructure.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import social.application.TimelineApi;
import social.application.collaborator.Clock;
import social.infrastructure.collaborator.MessageFormatter;

import java.util.stream.Collectors;

public class TimelineHandler {

    private static final String PATH_VARIABLE_USER = "user";

    private final TimelineApi timelineApi;
    private final MessageFormatter messageFormatter;
    private final Clock clock;

    public TimelineHandler(TimelineApi timelineApi, MessageFormatter messageFormatter, Clock clock) {
        this.timelineApi = timelineApi;
        this.messageFormatter = messageFormatter;
        this.clock = clock;
    }

    public Mono<ServerResponse> postMessage(ServerRequest request) {
        return
            request
                .bodyToMono(String.class)
                .doOnNext(body -> timelineApi.postMessageFor(request.pathVariable(PATH_VARIABLE_USER), body))
                .then(
                    ServerResponse
                        .status(HttpStatus.CREATED)
                        .build()
                );
    }

    public Mono<ServerResponse> readUserMessages(ServerRequest request) {
        Mono<String> body =
            Mono
                .fromCallable(() -> request.pathVariable(PATH_VARIABLE_USER))
                .flatMapIterable(user -> timelineApi.getMessagesFor(user))
                .map(userMessage -> messageFormatter.formatForRead(userMessage, clock.currentTime()))
                .collect(Collectors.joining("\n"));

        return
            ServerResponse
                .status(HttpStatus.OK)
                .body(body, String.class);
    }
}
