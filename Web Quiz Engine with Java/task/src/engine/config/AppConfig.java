package engine.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import engine.WebQuizEngine;
import org.modelmapper.ModelMapper;
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

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
