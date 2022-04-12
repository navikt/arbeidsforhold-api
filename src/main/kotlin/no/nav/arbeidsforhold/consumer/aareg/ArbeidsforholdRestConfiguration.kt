package no.nav.arbeidsforhold.consumer.aareg

import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.arbeidsforhold.consumer.tokendings.TokenDingsService
import org.glassfish.jersey.client.ClientProperties
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
open class ArbeidsforholdRestConfiguration {
    @Bean
    @Throws(URISyntaxException::class)
    open fun arbeidsforholdConsumer(
        @Named("arbeidsforholdClient") client: Client,
        @Value("\${AAREG_API_URL}") aaregServiceUri: String,
        tokenDingsService: TokenDingsService
    ): ArbeidsforholdConsumer {
        return ArbeidsforholdConsumer(client, URI(aaregServiceUri), tokenDingsService)
    }

    @Bean
    open fun arbeidsforholdClient(
        clientObjectMapperResolver: ContextResolver<ObjectMapper?>?,
        @Named("defaultConnectTimeoutInMillis") connectTimeout: Int?,
        @Named("defaultReadTimeoutInMillis") readTimeout: Int?
    ): Client {
        val client = ClientBuilder.newBuilder()
            .register(clientObjectMapperResolver)
            .build()
        client.property(ClientProperties.CONNECT_TIMEOUT, connectTimeout)
        client.property(ClientProperties.READ_TIMEOUT, readTimeout)
        return client
    }
}