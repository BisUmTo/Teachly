package net.delugan.teachly.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/logout", "/error").permitAll()
                        .requestMatchers("/cookies", "/tos", "/privacy").permitAll()
                        .requestMatchers("/api/v1", "/api", "/docs").permitAll()
                        .requestMatchers("/js/**", "/css/**", "/img/**", "/json/**", "/plugins/**", "/fonts/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
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
