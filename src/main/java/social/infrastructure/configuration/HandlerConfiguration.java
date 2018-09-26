package social.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import social.application.TimelineApi;
import social.application.collaborator.Clock;
import social.infrastructure.collaborator.MessageFormatter;
import social.infrastructure.handler.HealthHandler;
import social.infrastructure.handler.TimelineHandler;

@Configuration
public class HandlerConfiguration {

    @Bean
    public TimelineHandler timelineHandler(
        TimelineApi timelineApi,
        MessageFormatter messageFormatter,
        Clock clock
    ) {
        return new TimelineHandler(timelineApi, messageFormatter, clock);
    }

    @Bean
    public HealthHandler healthHandler() {
        return new HealthHandler();
    }
}
