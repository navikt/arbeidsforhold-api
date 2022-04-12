package no.nav.arbeidsforhold.consumer.kodeverk

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
open class KodeverkRestConfiguration {
    @Bean
    @Throws(URISyntaxException::class)
    open fun kodeverkConsumer(
        @Named("kodeverkClient") client: Client,
        @Value("\${KODEVERK_REST_API_URL}") kodeServiceUri: String?,
        tokenDingsService: TokenDingsService
    ): KodeverkConsumer {
        return KodeverkConsumer(client, URI(kodeServiceUri), tokenDingsService)
    }

    @Bean
    open fun kodeverkClient(
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