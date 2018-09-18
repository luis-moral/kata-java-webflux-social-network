package social.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import social.application.TimelineApi;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public TimelineApi timelineApi() {
        return new TimelineApi();
    }
}
