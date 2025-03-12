package net.delugan.teachly.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for application security settings.
 * Defines security rules for HTTP requests and authentication.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    /**
     * Configures the security filter chain for the application.
     * Defines which endpoints are accessible without authentication and which require authentication.
     * Also configures OAuth2 login.
     *
     * @param http The HttpSecurity to configure
     * @return The configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/logout", "/error").permitAll()
                        .requestMatchers("/cookies", "/tos", "/privacy").permitAll()
                        .requestMatchers("/api/v1", "/api", "/docs", "/javadocs").permitAll()
                        .requestMatchers("/js/**", "/css/**", "/img/**", "/json/**", "/plugins/**", "/fonts/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/javadocs/**").permitAll()
                        .requestMatchers("/api/v1/**").authenticated()
                        .requestMatchers("/dashboard/**").authenticated()
                        .anyRequest().denyAll()
                )
                .oauth2Login(auth -> auth
                        .loginPage("/login")
                        /*.successHandler((request, response, authentication) ->
                                response.sendRedirect("/dashboard")
                        )*/
                )
                .build();
    }
}
