package social.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import social.application.TimelineApi;
import social.infrastructure.repository.MessageRepository;

@Configuration
public class ServiceConfiguration {

    @Bean
    public TimelineApi timelineApi(MessageRepository messageRepository) {
        return new TimelineApi(messageRepository);
    }
}
