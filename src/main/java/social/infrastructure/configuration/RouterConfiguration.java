package social.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import social.infrastructure.handler.TimelineHandler;

@Configuration
public class RouterConfiguration {

    @Value("${timeline.path}")
    private String timelinePath;

    @Bean
    public RouterFunction<ServerResponse> timelineRouter(TimelineHandler timelineHandler)
    {
        return
            RouterFunctions
                .route(RequestPredicates.POST(timelinePath), timelineHandler::writeMessage);
    }
}
