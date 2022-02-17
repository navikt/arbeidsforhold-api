package no.nav.arbeidsforhold.services.kodeverk;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.arbeidsforhold.services.tokendings.TokenDingsService;
import no.nav.security.token.support.jaxrs.JwtTokenClientRequestFilter;
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
public class KodeverkRestConfiguration {

    @Bean
    public KodeverkConsumer kodeverkConsumer(
            @Named("kodeverkClient") Client client,
            @Value("${KODEVERK_REST_API_URL}") String kodeServiceUri,
            TokenDingsService tokenDingsService) throws URISyntaxException {
        return new KodeverkConsumer(client, new URI(kodeServiceUri), tokenDingsService);
    }

    @Bean
    public Client kodeverkClient(
            ContextResolver<ObjectMapper> clientObjectMapperResolver,
            @Named("defaultConnectTimeoutInMillis") Integer connectTimeout,
            @Named("defaultReadTimeoutInMillis") Integer readTimeout) {
        Client client = ClientBuilder.newBuilder()
                .register(JwtTokenClientRequestFilter.class)
                .register(clientObjectMapperResolver)
                .build();
        client.property(ClientProperties.CONNECT_TIMEOUT, connectTimeout);
        client.property(ClientProperties.READ_TIMEOUT, readTimeout);
        return client;
    }

}

