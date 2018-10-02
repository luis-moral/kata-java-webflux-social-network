package social.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import social.application.TimelineApi;
import social.application.collaborator.Clock;
import social.infrastructure.repository.MessageRepository;
import social.infrastructure.repository.UserRepository;

@Configuration
public class ServiceConfiguration {

    @Bean
    public TimelineApi timelineApi(UserRepository userRepository, MessageRepository messageRepository, Clock clock) {
        return new TimelineApi(userRepository, messageRepository, clock);
    }
}
