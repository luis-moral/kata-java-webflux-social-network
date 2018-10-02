package social.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import social.infrastructure.repository.MessageRepository;
import social.infrastructure.repository.UserRepository;

@Configuration
public class RepositoryConfiguration {

    @Bean
    public UserRepository userRepository() {
        return new UserRepository();
    }

    @Bean
    public MessageRepository messageRepository() {
        return new MessageRepository();
    }
}
