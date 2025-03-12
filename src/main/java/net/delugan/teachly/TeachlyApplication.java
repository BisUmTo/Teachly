package net.delugan.teachly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Main application class for the Teachly system.
 * This class serves as the entry point for the Spring Boot application.
 * It enables Spring Boot auto-configuration and web security features.
 */
@SpringBootApplication
@EnableWebSecurity
public class TeachlyApplication {
    /**
     * The main method that starts the Teachly application.
     * 
     * @param args Command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(TeachlyApplication.class, args);
    }
}
