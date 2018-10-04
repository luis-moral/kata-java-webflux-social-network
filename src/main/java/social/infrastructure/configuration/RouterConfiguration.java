package social.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import social.infrastructure.handler.HealthHandler;
import social.infrastructure.handler.UserHandler;

@Configuration
public class RouterConfiguration {

    @Value("${health.path}")
    private String healthPath;

    @Value("${user.timeline.path}")
    private String userTimelinePath;

    @Value("${user.follow.path}")
    private String userFollowPath;

    @Value("${user.wall.path}")
    private String userWallPath;

    @Bean
    public RouterFunction<ServerResponse> routes(
        HealthHandler healthHandler,
        UserHandler userHandler
    ) {
        return
            RouterFunctions
                .route(
                    RequestPredicates.GET(healthPath),
                    healthHandler::health
                )
                .andRoute(
                    RequestPredicates.GET(userTimelinePath),
                    userHandler::readUserMessages
                )
                .andRoute(
                    RequestPredicates.POST(userTimelinePath),
                    userHandler::postMessage
                )
                .andRoute(
                    RequestPredicates.POST(userFollowPath),
                    userHandler::followUser
                )
                .andRoute(
                    RequestPredicates.GET(userWallPath),
                    userHandler::readUserWall
                );
    }
}
