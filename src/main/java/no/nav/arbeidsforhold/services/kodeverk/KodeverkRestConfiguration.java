package no.nav.arbeidsforhold.services.kodeverk;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.security.oidc.jaxrs.OidcClientRequestFilter;
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
public class KodeverkRestConfiguration {

    @Value("${ARBEIDSFORHOLD-API-KODEVERK_REST-API-APIKEY_USERNAME}")
    private String kodeverkApiKeyUsername;

    @Value("${ARBEIDSFORHOLD-API-KODEVERK_REST-API-APIKEY_PASSWORD}")
    private String kodeverkApiKeyPassword;

    @Bean
    public KodeverkConsumer kodeverkConsumer(
            @Named("kodeverkClient") Client client,
            @Value("${KODEVERK_REST_API_URL}") String kodeServiceUri) throws URISyntaxException {
        return new KodeverkConsumer(client, new URI(kodeServiceUri));
    }

    @Bean
    public Client kodeverkClient(ContextResolver<ObjectMapper> clientObjectMapperResolver) {
        return ClientBuilder.newBuilder()
                .register(OidcClientRequestFilter.class)
                .register(clientObjectMapperResolver)
                .register((ClientRequestFilter) requestContext -> requestContext.getHeaders().putSingle(kodeverkApiKeyUsername,kodeverkApiKeyPassword))
                .build();
    }

}
