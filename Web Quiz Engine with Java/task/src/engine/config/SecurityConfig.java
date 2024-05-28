package engine.config;

import engine.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
public class SecurityConfig {

    /**
     * Creates and returns a bean of type PasswordEncoder using BCrypt algorithm with a work factor and secure
     *  random number generator
     * */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(14, new SecureRandom());
    }

    /**
     * Configures and returns the Spring Security filter chain. This method defines URL authorization rules and authentication
     *  mechanisms for the application.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserService userService) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(toH2Console()).permitAll()
                .antMatchers("/actuator/shutdown").permitAll()
                .antMatchers("/api/register").permitAll()
                .anyRequest().authenticated());

        http.httpBasic().authenticationEntryPoint(new RestAuthEntryPoint());
        http.userDetailsService(userService);
        http.csrf().disable(); // postman POST requests
        http.headers().frameOptions().disable(); // h2 console
        return http.build();
    }

    /**
     * A custom implementation of AuthenticationEntryPoint interface used to handle unauthorized access attempts.
     */
    public static class RestAuthEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
            // Send 401 UNAUTHORIZED HTTP status code with a message when unauthorized access is made
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }

}
