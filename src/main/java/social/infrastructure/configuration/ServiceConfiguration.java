package social.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import social.application.UserApi;
import social.application.collaborator.Clock;
import social.infrastructure.repository.MessageRepository;
import social.infrastructure.repository.UserRepository;

@Configuration
public class ServiceConfiguration {

    @Bean
    public UserApi userApi(UserRepository userRepository, MessageRepository messageRepository, Clock clock) {
        return new UserApi(userRepository, messageRepository, clock);
    }
}
