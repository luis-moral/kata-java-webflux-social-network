package social.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import social.application.collaborator.Clock;
import social.infrastructure.collaborator.SystemClock;

@Configuration
public class CollaboratorConfiguration {

    @Bean
    public Clock clock() {
        return new SystemClock();
    }
}
