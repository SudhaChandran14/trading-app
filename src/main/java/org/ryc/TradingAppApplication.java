package org.ryc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Marks this as the main Spring Boot entry point
public class TradingAppApplication {

    public static void main(String[] args) {
        // This starts the entire Spring Boot server on port 8080
        SpringApplication.run(TradingAppApplication.class, args);
    }
}