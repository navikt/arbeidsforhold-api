package no.nav.arbeidsforhold.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.tokendings.TokenDingsService;
import org.glassfish.jersey.client.ClientProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.ext.ContextResolver;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class ArbeidsforholdRestConfiguration {

    @Bean
    public ArbeidsforholdConsumer arbeidsforholdConsumer(
            @Named("arbeidsforholdClient") Client client,
            @Value("${AAREG_API_URL}") String aaregServiceUri,
            TokenDingsService tokenDingsService) throws URISyntaxException {
        return new ArbeidsforholdConsumer(client, new URI(aaregServiceUri), tokenDingsService);
    }

    @Bean
    public Client arbeidsforholdClient(
            ContextResolver<ObjectMapper> clientObjectMapperResolver,
            @Named("defaultConnectTimeoutInMillis") Integer connectTimeout,
            @Named("defaultReadTimeoutInMillis") Integer readTimeout) {
        Client client = ClientBuilder.newBuilder()
                .register(clientObjectMapperResolver)
                .build();
        client.property(ClientProperties.CONNECT_TIMEOUT, connectTimeout);
        client.property(ClientProperties.READ_TIMEOUT, readTimeout);
        return client;
    }
}
