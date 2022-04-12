package no.nav.arbeidsforhold.consumer.ereg

import no.nav.arbeidsforhold.consumer.ereg.domain.EregOrganisasjon
import no.nav.arbeidsforhold.consumer.tokendings.TokenDingsService
import no.nav.arbeidsforhold.exception.EregConsumerException
import no.nav.arbeidsforhold.util.getToken
import no.nav.arbeidsforhold.util.readEntity
import no.nav.common.log.MDCConstants
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.beans.factory.annotation.Value
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response

private const val CONSUMER_ID = "personbruker-arbeidsforhold-api"
private const val BEARER = "Bearer "

class EregConsumer(
    private val client: Client,
    private val endpoint: URI,
    private val tokenDingsService: TokenDingsService
) {
    private val log = LoggerFactory.getLogger(EregConsumer::class.java)

    @Value("\${PERSONOPPLYSNINGER_PROXY_TARGET_APP}")
    private val targetApp: String? = null

    fun hentOrgnavn(orgnr: String?, gyldigDato: String?): EregOrganisasjon? {
        val accessToken = tokenDingsService.exchangeToken(getToken(), targetApp).accessToken
        val request = buildOrgnrRequest(orgnr, gyldigDato, accessToken)
        try {
            request.get().use { response -> return readResponse(response) }
        } catch (e: EregConsumerException) {
            val msg = String.format("Oppslag på orgnr %s med dato %s feilet. ", orgnr, gyldigDato)
            log.error(msg + e.message)
        } catch (e: Exception) {
            val msg = "Forsøkte å konsumere REST-tjenesten Enhetsregisteret. endpoint=[$endpoint]. Exception message="
            log.error(msg + e.message)
        }
        return null
    }

    private fun buildOrgnrRequest(orgnr: String?, gyldigDato: String?, accessToken: String): Invocation.Builder {
        val gyldigDatoSubstring = gyldigDato?.substring(0, 10)
        return client.target(endpoint)
            .path("v1/organisasjon/$orgnr/noekkelinfo")
            .queryParam("gyldigDato", gyldigDatoSubstring)
            .request()
            .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
            .header("Nav-Consumer-Token", getToken())
            .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
            .header("Nav-Consumer-Id", CONSUMER_ID)
    }

    private fun readResponse(r: Response): EregOrganisasjon {
        return if (Response.Status.Family.SUCCESSFUL != r.statusInfo.family) {
            val msg =
                "Forsøkte å konsumere REST-tjenesten Enhetsregister. endpoint=[" + endpoint + "], HTTP response status=[" + r.status + "]."
            throw EregConsumerException(msg + " - " + readEntity(String::class.java, r))
        } else {
            readEntity(EregOrganisasjon::class.java, r)
        }
    }
}