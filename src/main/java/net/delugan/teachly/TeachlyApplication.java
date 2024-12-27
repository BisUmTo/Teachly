package net.delugan.teachly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class TeachlyApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeachlyApplication.class, args);
    }
}
