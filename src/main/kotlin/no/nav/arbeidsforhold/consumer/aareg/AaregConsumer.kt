package no.nav.arbeidsforhold.consumer.aareg

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import no.nav.arbeidsforhold.config.Environment
import no.nav.arbeidsforhold.config.MDC_CALL_ID
import no.nav.arbeidsforhold.consumer.aareg.dto.Arbeidsforhold
import no.nav.arbeidsforhold.exception.ConsumerException
import no.nav.tms.token.support.tokendings.exchange.TokendingsService
import org.slf4j.MDC

class AaregConsumer(
    private val client: HttpClient,
    private val environment: Environment,
    private val tokenDingsService: TokendingsService
) {
    suspend fun hentArbeidsforholdmedFnr(token: String, fnr: String): List<Arbeidsforhold> {
        val accessToken = tokenDingsService.exchangeToken(token, environment.aaregTargetApp)

        val response = client.get(environment.aaregApiUrl.plus("/api/v2/arbeidstaker/arbeidsforhold")) {
            parameter("regelverk", REGELVERK)
            parameter("sporingsinformasjon", false)
            parameter("arbeidsforholdtype", ARBEIDSFORHOLDTYPER)
            parameter("arbeidsforholdstatus", ARBEIDSFORHOLDSTATUS)
            header(HttpHeaders.Authorization, BEARER + accessToken)
            header("Nav-Call-Id", MDC.get(MDC_CALL_ID))
            header("Nav-Personident", fnr)
        }

        if (response.status.isSuccess()) {
            return response.body()
        } else {
            throw ConsumerException("Oppslag mot AAREG med fnr feilet med status: ${response.status}")
        }
    }

    suspend fun hentArbeidsforholdmedId(token: String, fnr: String, id: Int): Arbeidsforhold {
        val accessToken = tokenDingsService.exchangeToken(token, environment.aaregTargetApp)

        val response = client.get(environment.aaregApiUrl.plus("/api/v2/arbeidsforhold/$id")) {
            parameter("historikk", true)
            parameter("sporingsinformasjon", false)
            header(HttpHeaders.Authorization, BEARER + accessToken)
            header("Nav-Call-Id", MDC.get(MDC_CALL_ID))
            header("Nav-Personident", fnr)
        }

        if (response.status.isSuccess()) {
            return response.body()
        } else {
            throw ConsumerException("Oppslag mot AAREG med id feilet med status: ${response.status}")
        }
    }

    companion object {
        private const val BEARER = "Bearer "
        private const val REGELVERK = "A_ORDNINGEN"
        private const val ARBEIDSFORHOLDTYPER =
            "ordinaertArbeidsforhold,maritimtArbeidsforhold,forenkletOppgjoersordning,frilanserOppdragstakerHonorarPersonerMm"
        private const val ARBEIDSFORHOLDSTATUS = "AKTIV,FREMTIDIG,AVSLUTTET"
    }
}