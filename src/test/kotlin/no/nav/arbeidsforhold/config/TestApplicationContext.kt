package no.nav.arbeidsforhold.config

import io.ktor.client.HttpClient
import no.nav.arbeidsforhold.consumer.aareg.AaregConsumer
import no.nav.arbeidsforhold.consumer.ereg.EregConsumer
import no.nav.arbeidsforhold.service.ArbeidsforholdService

class TestApplicationContext(httpClient: HttpClient) {

    val env = Environment()

    val tokendingsService = DummyTokendingsService()
    val aaregConsumer = AaregConsumer(httpClient, env, tokendingsService)
    val eregConsumer = EregConsumer(httpClient, env)
    val arbeidsforholdService = ArbeidsforholdService(aaregConsumer, eregConsumer)
}