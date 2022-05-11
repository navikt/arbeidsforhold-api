package no.nav.arbeidsforhold.consumer.aareg

import no.nav.arbeidsforhold.consumer.aareg.domain.Arbeidsforhold
import no.nav.arbeidsforhold.consumer.tokendings.TokenDingsService
import no.nav.arbeidsforhold.exception.ArbeidsforholdConsumerException
import no.nav.arbeidsforhold.util.getToken
import no.nav.arbeidsforhold.util.readEntity
import no.nav.common.log.MDCConstants
import org.slf4j.MDC
import org.springframework.beans.factory.annotation.Value
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.GenericType
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response

private const val CONSUMER_ID = "personbruker-arbeidsforhold-api"
private const val BEARER = "Bearer "
private const val REGELVERK = "A_ORDNINGEN"
private const val ARBEIDSFORHOLDTYPER =
    "ordinaertArbeidsforhold,maritimtArbeidsforhold,forenkletOppgjoersordning,frilanserOppdragstakerHonorarPersonerMm"

class ArbeidsforholdConsumer(
    private val client: Client,
    private val endpoint: URI,
    private val tokenDingsService: TokenDingsService
) {
    @Value("\${AAREG_TARGET_APP}")
    private val targetApp: String? = null

    fun hentArbeidsforholdmedFnr(fnr: String): List<Arbeidsforhold> {
        val accessToken = tokenDingsService.exchangeToken(getToken(), targetApp).accessToken
        val request = buildFnrRequest(fnr, accessToken)
        return hentArbeidsforholdmedFnr(request)
    }

    fun hentArbeidsforholdmedId(fnr: String, id: Int): Arbeidsforhold {
        val accessToken = tokenDingsService.exchangeToken(getToken(), targetApp).accessToken
        val request = buildForholdIdRequest(fnr, id, accessToken)
        return hentArbeidsforholdmedId(request)
    }

    private fun buildFnrRequest(fnr: String, accessToken: String): Invocation.Builder {
        return client.target(endpoint)
            .path("api/v2/arbeidstaker/arbeidsforhold")
            .queryParam("regelverk", REGELVERK)
            .queryParam("sporingsinformasjon", false)
            .queryParam("arbeidsforholdtype", ARBEIDSFORHOLDTYPER)
            .request()
            .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
            .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
            .header("Nav-Consumer-Id", CONSUMER_ID)
            .header("Nav-Personident", fnr)
    }

    private fun buildForholdIdRequest(fnr: String, id: Int, accessToken: String): Invocation.Builder {
        return client.target(endpoint)
            .path("api/v2/arbeidsforhold/$id")
            .queryParam("historikk", true)
            .queryParam("sporingsinformasjon", false)
            .request()
            .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
            .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
            .header("Nav-Consumer-Id", CONSUMER_ID)
            .header("Nav-Personident", fnr)
    }

    private fun hentArbeidsforholdmedFnr(request: Invocation.Builder): List<Arbeidsforhold> {
        try {
            request.get().use { response -> return readFnrResponse(response) }
        } catch (e: ArbeidsforholdConsumerException) {
            throw e
        } catch (e: Exception) {
            val msg = "Forsøkte å konsumere REST-tjenesten Arbeidsforhold. endpoint=[$endpoint]."
            throw ArbeidsforholdConsumerException(msg, e)
        }
    }

    private fun hentArbeidsforholdmedId(request: Invocation.Builder): Arbeidsforhold {
        try {
            request.get().use { response -> return readIdResponse(response) }
        } catch (e: ArbeidsforholdConsumerException) {
            throw e
        } catch (e: Exception) {
            val msg = "Forsøkte å konsumere REST-tjenesten Arbeidsforhold. endpoint=[$endpoint]."
            throw ArbeidsforholdConsumerException(msg, e)
        }
    }

    private fun readFnrResponse(r: Response): List<Arbeidsforhold> {
        return if (Response.Status.Family.SUCCESSFUL != r.statusInfo.family) {
            val msg =
                "Forsøkte å konsumere REST-tjenesten Arbeidsforhold. endpoint=[" + endpoint + "], HTTP response status=[" + r.status + "]."
            throw ArbeidsforholdConsumerException(msg + " - " + readEntity(String::class.java, r))
        } else {
            r.readEntity(object : GenericType<List<Arbeidsforhold>>() {})
        }
    }

    private fun readIdResponse(r: Response): Arbeidsforhold {
        return if (Response.Status.Family.SUCCESSFUL != r.statusInfo.family) {
            val msg =
                "Forsøkte å konsumere REST-tjenesten Arbeidsforhold. endpoint=[" + endpoint + "], HTTP response status=[" + r.status + "]."
            throw ArbeidsforholdConsumerException(msg + " - " + readEntity(String::class.java, r))
        } else {
            r.readEntity(Arbeidsforhold::class.java)
        }
    }
}