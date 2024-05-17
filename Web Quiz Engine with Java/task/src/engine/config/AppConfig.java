package engine.config;

import engine.WebQuizEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Logger appLogger() {
        return LoggerFactory.getLogger(WebQuizEngine.class);
    }

}
