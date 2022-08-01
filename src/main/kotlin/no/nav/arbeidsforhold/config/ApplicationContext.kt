package no.nav.arbeidsforhold.config

import no.nav.arbeidsforhold.consumer.aareg.AaregConsumer
import no.nav.arbeidsforhold.consumer.ereg.EregConsumer
import no.nav.arbeidsforhold.service.ArbeidsforholdService
import no.nav.tms.token.support.tokendings.exchange.TokendingsServiceBuilder

class ApplicationContext {

    val env = Environment()
    val httpClient = HttpClientBuilder.build()

    val tokendingsService = TokendingsServiceBuilder.buildTokendingsService()
    val aaregConsumer = AaregConsumer(httpClient, env, tokendingsService)
    val eregConsumer = EregConsumer(httpClient, env)
    val arbeidsforholdService = ArbeidsforholdService(aaregConsumer, eregConsumer)
}