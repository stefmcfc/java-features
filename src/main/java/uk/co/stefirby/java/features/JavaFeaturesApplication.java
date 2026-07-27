package uk.co.stefirby.java.features;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Java 21 / Spring Boot 4.1: minimal application entry point wiring up the
 * Spring context so later stages have a running host for the thin REST API
 * layer described in {@code specs/modern-java-features-spec.md}.
 */
@SpringBootApplication
public class JavaFeaturesApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaFeaturesApplication.class, args);
    }
}
