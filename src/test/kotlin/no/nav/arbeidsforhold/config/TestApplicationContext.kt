package no.nav.arbeidsforhold.config

import no.nav.arbeidsforhold.config.mock.setupMockedClient
import no.nav.arbeidsforhold.consumer.aareg.AaregConsumer
import no.nav.arbeidsforhold.consumer.ereg.EregConsumer
import no.nav.arbeidsforhold.service.ArbeidsforholdService

class TestApplicationContext {

    val env = Environment()
    val httpClient = setupMockedClient()

    val tokendingsService = DummyTokendingsService()
    val aaregConsumer = AaregConsumer(httpClient, env, tokendingsService)
    val eregConsumer = EregConsumer(httpClient, env)
    val arbeidsforholdService = ArbeidsforholdService(aaregConsumer, eregConsumer)
}