package no.nav.arbeidsforhold.api;

import no.nav.arbeidsforhold.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class Launcher {

    public static void main(String... args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }


}
