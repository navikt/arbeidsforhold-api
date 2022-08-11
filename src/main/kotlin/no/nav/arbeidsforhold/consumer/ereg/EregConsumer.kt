package no.nav.arbeidsforhold.consumer.ereg

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import no.nav.arbeidsforhold.config.Environment
import no.nav.arbeidsforhold.config.MDC_CALL_ID
import no.nav.arbeidsforhold.consumer.ereg.dto.EregOrganisasjon
import org.slf4j.LoggerFactory
import org.slf4j.MDC


class EregConsumer(private val client: HttpClient, private val environment: Environment) {

    private val logger = LoggerFactory.getLogger(EregConsumer::class.java)

    suspend fun hentOrgnavn(orgnr: String, gyldigDato: String?): String {
        val gyldigDatoSubstring = gyldigDato?.substring(0, 10) // TODO: Nødvendig?

        val eregResponse: HttpResponse =
            client.get(environment.eregApiUrl.plus("/v1/organisasjon/$orgnr/noekkelinfo")) {
                parameter("gyldigDato", gyldigDatoSubstring)
                header("Nav-Call-Id", MDC.get(MDC_CALL_ID))
            }
        return if (eregResponse.status.isSuccess()) {
            val eregOrganisasjon = eregResponse.body<EregOrganisasjon>()
            eregOrganisasjon.navn.getNavn()
        } else {
            logger.warn("Oppslag mot EEREG feilet med status: ${eregResponse.status}")
            orgnr
        }
    }
}