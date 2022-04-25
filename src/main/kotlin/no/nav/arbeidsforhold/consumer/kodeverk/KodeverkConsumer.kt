package no.nav.arbeidsforhold.consumer.kodeverk

import no.nav.arbeidsforhold.consumer.kodeverk.domain.GetKodeverkKoderBetydningerResponse
import no.nav.arbeidsforhold.exception.KodeverkConsumerException
import no.nav.arbeidsforhold.util.readEntity
import no.nav.common.log.MDCConstants
import org.slf4j.MDC
import org.springframework.cache.annotation.Cacheable
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.Response

private const val SPRAAK = "nb"
private const val CONSUMER_ID = "personbruker-arbeidsforhold-api"

open class KodeverkConsumer(
    private val client: Client,
    private val endpoint: URI,
) {
    @Cacheable("yrker")
    open fun hentYrke(): GetKodeverkKoderBetydningerResponse {
        return hentKodeverkBetydning(getBuilder("/api/v1/kodeverk/Yrker/koder/betydninger"))
    }

    @Cacheable("arbeidsforholdstyper")
    open fun hentArbeidsforholdstyper(): GetKodeverkKoderBetydningerResponse {
        return hentKodeverkBetydning(getBuilder("/api/v1/kodeverk/Arbeidsforholdstyper/koder/betydninger"))
    }

    @Cacheable("arbeidstidsordninger")
    open fun hentArbeidstidsordningstyper(): GetKodeverkKoderBetydningerResponse {
        return hentKodeverkBetydning(getBuilder("/api/v1/kodeverk/Arbeidstidsordninger/koder/betydninger"))
    }

    @Cacheable("fartsomraader")
    open fun hentFartsomraade(): GetKodeverkKoderBetydningerResponse {
        return hentKodeverkBetydning(getBuilder("/api/v1/kodeverk/Fartsområder/koder/betydninger"))
    }

    @Cacheable("skipsregistre")
    open fun hentSkipsregister(): GetKodeverkKoderBetydningerResponse {
        return hentKodeverkBetydning(getBuilder("/api/v1/kodeverk/Skipsregistre/koder/betydninger"))
    }

    @Cacheable("skipstyper")
    open fun hentSkipstyper(): GetKodeverkKoderBetydningerResponse {
        return hentKodeverkBetydning(getBuilder("/api/v1/kodeverk/Skipstyper/koder/betydninger"))
    }

    @Cacheable("land")
    open fun hentLand(): GetKodeverkKoderBetydningerResponse {
        return hentKodeverkBetydning(getBuilder("/api/v1/kodeverk/LandkoderISO2/koder/betydninger"))
    }

    @Cacheable("permisjonstyper")
    open fun hentPermisjonstype(): GetKodeverkKoderBetydningerResponse {
        return hentKodeverkBetydning(getBuilder("/api/v1/kodeverk/PermisjonsOgPermitteringsBeskrivelse/koder/betydninger"))
    }

    @Cacheable("sluttaarsaker")
    open fun hentSluttAarsak(): GetKodeverkKoderBetydningerResponse {
        return hentKodeverkBetydning(getBuilder("/api/v1/kodeverk/SluttårsakAareg/koder/betydninger"))
    }

    private fun getBuilder(path: String): Invocation.Builder {
        return client.target(endpoint)
            .path(path)
            .queryParam("spraak", SPRAAK)
            .queryParam("ekskluderUgyldige", false)
            .request()
            .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
            .header("Nav-Consumer-Id", CONSUMER_ID)
    }

    private fun hentKodeverkBetydning(request: Invocation.Builder): GetKodeverkKoderBetydningerResponse {
        try {
            request.get().use { response -> return readResponseBetydning(response) }
        } catch (e: KodeverkConsumerException) {
            throw e
        } catch (e: Exception) {
            val msg = "Forsøkte å konsumere kodeverk. endpoint=[$endpoint]."
            throw KodeverkConsumerException(msg, e)
        }
    }

    private fun readResponseBetydning(r: Response): GetKodeverkKoderBetydningerResponse {
        return if (Response.Status.Family.SUCCESSFUL != r.statusInfo.family) {
            val msg =
                "Forsøkte å konsumere kodeverk. endpoint=[" + endpoint + "], HTTP response status=[" + r.status + "]."
            throw KodeverkConsumerException(
                "$msg - " + readEntity(
                    String::class.java,
                    r
                )
            )
        } else {
            readEntity(
                GetKodeverkKoderBetydningerResponse::class.java,
                r
            )
        }
    }
}