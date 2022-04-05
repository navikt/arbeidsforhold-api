package no.nav.arbeidsforhold.api;

import no.nav.arbeidsforhold.config.ApplicationConfig;
import no.nav.security.token.support.test.FileResourceRetriever;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@Import({ApplicationConfig.class})
public class TestLauncher {

    public static void main(String... args) {
        setTestEnvironment();
        ArrayList<String> argList = new ArrayList<>(Arrays.asList(args));
        argList.add("--spring.profiles.active=dev");
        SpringApplication.run(ApplicationConfig.class, argList.toArray(new String[0]));
    }

    /**
     * To be able to ovverride the oidc validation properties in
     * EnableOIDCTokenValidationConfiguration in oidc-spring-support
     */
    @Bean
    @Primary
    FileResourceRetriever overrideOidcResourceRetriever() {
        return new FileResourceRetriever("/metadata.json", "/jwkset.json");
    }

    @Bean
    @Primary
    public ResourceConfig testJerseyConfig() {
        return new TestRestResourceConfiguration();
    }

    private static void setTestEnvironment() {
        // Properties som brukes i logback-default-without-abac.xml
        System.setProperty("APP_LOG_HOME", "target/log");
        System.setProperty("contextName", "arbeidsforhold-api");

        System.setProperty("AAD_B2C_CLIENTID_USERNAME", "");
        System.setProperty("AAD_B2C_DISCOVERY_URL", "");
        System.setProperty("EREG_API_URL", "");
        System.setProperty("SECURITY_TOKEN_SERVICE_TOKEN_URL", "");
        System.setProperty("KODEVERK_REST_API_URL", "");
        System.setProperty("AAREG_API_URL", "");
        System.setProperty("SRVARBEIDSFORHOLD_API_USERNAME", "");
        System.setProperty("SRVARBEIDSFORHOLD_API_PASSWORD", "");
    }
}
