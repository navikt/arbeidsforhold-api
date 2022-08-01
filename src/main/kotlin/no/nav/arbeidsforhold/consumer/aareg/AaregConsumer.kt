package no.nav.arbeidsforhold.consumer.aareg

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import no.nav.arbeidsforhold.config.Environment
import no.nav.arbeidsforhold.config.MDC_CALL_ID
import no.nav.arbeidsforhold.consumer.aareg.dto.Arbeidsforhold
import no.nav.tms.token.support.tokendings.exchange.TokendingsService
import org.slf4j.MDC

class AaregConsumer(
    private val client: HttpClient,
    private val environment: Environment,
    private val tokenDingsService: TokendingsService
) {
    private val BEARER = "Bearer "
    private val REGELVERK = "A_ORDNINGEN"
    private val ARBEIDSFORHOLDTYPER =
        "ordinaertArbeidsforhold,maritimtArbeidsforhold,forenkletOppgjoersordning,frilanserOppdragstakerHonorarPersonerMm"
    private val ARBEIDSFORHOLDSTATUS = "AKTIV,FREMTIDIG,AVSLUTTET"


    suspend fun hentArbeidsforholdmedFnr(token: String, fnr: String): List<Arbeidsforhold> {
        val accessToken = tokenDingsService.exchangeToken(token, environment.aaregTargetApp)

        val aaregResponse: HttpResponse =
            client.get(environment.aaregApiUrl.plus("/api/v2/arbeidstaker/arbeidsforhold")) {
                parameter("regelverk", REGELVERK)
                parameter("sporingsinformasjon", false)
                parameter("arbeidsforholdtype", ARBEIDSFORHOLDTYPER)
                parameter("arbeidsforholdstatus", ARBEIDSFORHOLDSTATUS)
                header(HttpHeaders.Authorization, BEARER + accessToken)
                header("Nav-Call-Id", MDC.get(MDC_CALL_ID))
                header("Nav-Personident", fnr)
            }
        return if (aaregResponse.status.isSuccess()) {
            aaregResponse.body()
        } else {
            throw RuntimeException("Oppslag mot AAREG med fnr feilet med status: ${aaregResponse.status} og melding: ${aaregResponse.body<String>()}")
        }
    }

    suspend fun hentArbeidsforholdmedId(token: String, fnr: String, id: Int): Arbeidsforhold {
        val accessToken = tokenDingsService.exchangeToken(token, environment.aaregTargetApp)

        val aaregResponse: HttpResponse =
            client.get(environment.aaregApiUrl.plus("/api/v2/arbeidsforhold/$id")) {
                parameter("historikk", true)
                parameter("sporingsinformasjon", false)
                header(HttpHeaders.Authorization, BEARER + accessToken)
                header("Nav-Call-Id", MDC.get(MDC_CALL_ID))
                header("Nav-Personident", fnr)
            }
        return if (aaregResponse.status.isSuccess()) {
            aaregResponse.body()
        } else {
            throw RuntimeException("Oppslag mot AAREG med id feilet med status: ${aaregResponse.status} og melding: ${aaregResponse.body<String>()}")
        }
    }
}