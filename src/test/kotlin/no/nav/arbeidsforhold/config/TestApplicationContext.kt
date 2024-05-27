package no.nav.arbeidsforhold.config

import io.ktor.client.HttpClient
import io.micrometer.prometheusmetrics.PrometheusConfig
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry
import no.nav.arbeidsforhold.consumer.aareg.AaregConsumer
import no.nav.arbeidsforhold.consumer.ereg.EregConsumer
import no.nav.arbeidsforhold.service.ArbeidsforholdService

class TestApplicationContext(httpClient: HttpClient) {

    val env = Environment(
        corsAllowedOrigins = "",
        corsAllowedSchemes = "https",
        eregApiUrl = "https://ereg",
        aaregApiUrl = "https://aareg",
        aaregTargetApp = "",
    )

    val appMicrometerRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)

    val tokendingsService = DummyTokendingsService()
    val aaregConsumer = AaregConsumer(httpClient, env, tokendingsService)
    val eregConsumer = EregConsumer(httpClient, env)
    val arbeidsforholdService = ArbeidsforholdService(aaregConsumer, eregConsumer)
}