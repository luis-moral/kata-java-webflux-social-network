package social.infrastructure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import social.application.TimelineApi;

@RestController
@RequestMapping(path = "${controller.timeline}")
public class TimelineController {

    private final TimelineApi timelineApi;

    @Autowired
    public TimelineController(TimelineApi timelineApi) {
        this.timelineApi = timelineApi;
    }

    @PostMapping
    public Mono<ResponseEntity<?>> writeMessage(
        @PathVariable("user") String user,
        @RequestBody String message
    ) {
        return
            Mono
                .fromRunnable(() -> timelineApi.postMessage(user, message))
                .map(value -> new ResponseEntity<>(HttpStatus.CREATED));
    }
}
