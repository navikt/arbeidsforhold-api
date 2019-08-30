package no.nav.arbeidsforhold.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ext.ContextResolver;

@Configuration
public class RestClientConfiguration {

    @Bean
    public Integer defaultConnectTimeoutInMillis() {
        return Integer.valueOf(10 * 1000);
    }

    @Bean
    public Integer defaultReadTimeoutInMillis() {
        return Integer.valueOf(2 * 1000);
    }

    @Bean
    public ContextResolver<ObjectMapper> clientObjectMapperResolver() {
        return type -> new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

}
