package no.nav.arbeidsforhold.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.security.token.support.jaxrs.JwtTokenClientRequestFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.ext.ContextResolver;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class EregRestConfiguration {

    @Bean
    public EregConsumer eregConsumer(
            @Named("eregClient") Client client,
            @Value("${EREG_API_URL}") String eregServiceUri) throws URISyntaxException {
        return new EregConsumer(client, new URI(eregServiceUri));
    }

    @Bean
    public Client eregClient(ContextResolver<ObjectMapper> clientObjectMapperResolver) {
        Client c =  ClientBuilder.newBuilder()
                .register(clientObjectMapperResolver)
                .register(JwtTokenClientRequestFilter.class)
                .build();
        return c;
    }

}
