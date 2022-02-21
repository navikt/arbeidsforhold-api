package no.nav.tokendings

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.arbeidsforhold.exceptions.ConsumerException
import no.nav.arbeidsforhold.util.consumerErrorMessage
import no.nav.tokendings.domain.TokendingsToken
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Entity
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.Form
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

class TokendingsConsumer constructor(
    private val client: Client,
    private val endpoint: URI
) {

    fun exchangeToken(subjectToken: String, clientAssertion: String, audience: String?): TokendingsToken {
        val form = Form()
            .param("client_assertion_type", "urn:ietf:params:oauth:client-assertion-type:jwt-bearer")
            .param("client_assertion", clientAssertion)
            .param("grant_type", "urn:ietf:params:oauth:grant-type:token-exchange")
            .param("subject_token_type", "urn:ietf:params:oauth:token-type:jwt")
            .param("subject_token", subjectToken)
            .param("audience", audience)

        buildRequest().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE)).use { response ->
            val responseBody = response.readEntity(String::class.java)
            if (Response.Status.Family.SUCCESSFUL != response.statusInfo.family) {
                throw ConsumerException(consumerErrorMessage(endpoint, response.status, responseBody))
            }
            return jacksonObjectMapper().readValue(responseBody)
        }
    }

    private fun buildRequest(): Invocation.Builder {
        return client.target(endpoint).request()
    }
}
