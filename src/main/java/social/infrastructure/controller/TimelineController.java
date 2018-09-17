package social.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "${controller.timeline}")
public class TimelineController {

    public Mono<ResponseEntity<?>> writeMessage(@RequestBody String message) {
        return Mono.empty();
    }
}
