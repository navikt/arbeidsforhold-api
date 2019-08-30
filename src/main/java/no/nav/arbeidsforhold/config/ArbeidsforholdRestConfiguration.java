package no.nav.arbeidsforhold.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.security.oidc.jaxrs.OidcClientRequestFilter;
import org.glassfish.jersey.client.ClientProperties;
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
public class ArbeidsforholdRestConfiguration {

    @Value("${ARBEIDSFORHOLD_API_AAREG_API_APIKEY_USERNAME}")
    private String arbeidsforholdApiUsername;

    @Value("${ARBEIDSFORHOLD_API_AAREG_API_APIKEY_PASSWORD}")
    private String arbeidsforholdApiPassword;

    @Bean
    public ArbeidsforholdConsumer arbeidsforholdConsumer(
            @Named("arbeidsforholdClient") Client client,
            @Value("${AAREG_API_URL}") String aaregServiceUri) throws URISyntaxException {
        return new ArbeidsforholdConsumer(client, new URI(aaregServiceUri));
    }

    @Bean
    public Client arbeidsforholdClient(
            ContextResolver<ObjectMapper> clientObjectMapperResolver,
            @Named("defaultConnectTimeoutInMillis") Integer connectTimeout,
            @Named("defaultReadTimeoutInMillis") Integer readTimeout) {
        Client client =  ClientBuilder.newBuilder()
                .register(clientObjectMapperResolver)
                .register(OidcClientRequestFilter.class)
                .register((ClientRequestFilter) requestContext -> requestContext.getHeaders().putSingle(arbeidsforholdApiUsername, arbeidsforholdApiPassword))
                .build();
        client.property(ClientProperties.CONNECT_TIMEOUT, connectTimeout);
        client.property(ClientProperties.READ_TIMEOUT, readTimeout);
        return client;
    }

}
