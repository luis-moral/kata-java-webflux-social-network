package social.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import social.infrastructure.repository.MessageRepository;

@Configuration
public class RepositoryConfiguration {

    @Bean
    public MessageRepository messageRepository() {
        return new MessageRepository();
    }
}
