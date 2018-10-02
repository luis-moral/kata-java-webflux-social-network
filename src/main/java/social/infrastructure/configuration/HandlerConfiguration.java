package social.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import social.application.UserApi;
import social.application.collaborator.Clock;
import social.infrastructure.collaborator.MessageFormatter;
import social.infrastructure.handler.HealthHandler;
import social.infrastructure.handler.UserHandler;

@Configuration
public class HandlerConfiguration {

    @Bean
    public UserHandler userHandler(
        UserApi userApi,
        MessageFormatter messageFormatter,
        Clock clock
    ) {
        return new UserHandler(userApi, messageFormatter, clock);
    }

    @Bean
    public HealthHandler healthHandler() {
        return new HealthHandler();
    }
}
