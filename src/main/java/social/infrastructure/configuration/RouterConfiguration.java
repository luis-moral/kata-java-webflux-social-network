package social.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import social.infrastructure.handler.HealthHandler;
import social.infrastructure.handler.TimelineHandler;

@Configuration
public class RouterConfiguration {

    @Value("${health.path}")
    private String healthPath;

    @Value("${timeline.path}")
    private String timelinePath;

    @Bean
    public RouterFunction<ServerResponse> routes(
        TimelineHandler timelineHandler,
        HealthHandler healthHandler
    ) {
        return
            RouterFunctions
                .route(RequestPredicates.GET(healthPath), healthHandler::health)
                .andRoute(RequestPredicates.POST(timelinePath), timelineHandler::postMessage);
    }
}
