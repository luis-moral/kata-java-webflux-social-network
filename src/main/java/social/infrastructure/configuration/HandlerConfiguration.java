package social.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import social.application.TimelineApi;
import social.infrastructure.handler.HealthHandler;
import social.infrastructure.handler.TimelineHandler;

@Configuration
public class HandlerConfiguration {

    @Bean
    public TimelineHandler timelineHandler(TimelineApi timelineApi) {
        return new TimelineHandler(timelineApi);
    }

    @Bean
    public HealthHandler healthHandler() {
        return new HealthHandler();
    }
}
