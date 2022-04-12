package no.nav.arbeidsforhold.consumer.ereg

import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.arbeidsforhold.consumer.tokendings.TokenDingsService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URI
import java.net.URISyntaxException
import javax.inject.Named
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.ext.ContextResolver

@Configuration
open class EregRestConfiguration {
    @Bean
    @Throws(URISyntaxException::class)
    open fun eregConsumer(
        @Named("eregClient") client: Client,
        @Value("\${EREG_API_URL}") eregServiceUri: String,
        tokenDingsService: TokenDingsService
    ): EregConsumer {
        return EregConsumer(client, URI(eregServiceUri), tokenDingsService)
    }

    @Bean
    open fun eregClient(clientObjectMapperResolver: ContextResolver<ObjectMapper?>?): Client {
        return ClientBuilder.newBuilder()
            .register(clientObjectMapperResolver)
            .build()
    }
}