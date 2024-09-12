package no.nav.arbeidsforhold.integration

import io.kotest.matchers.shouldBe
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import no.nav.arbeidsforhold.config.mocks.setupMockedClient
import kotlin.test.Test

class HentArbeidsforholdArbeidstakerIT : IntegrationTest() {

    val HENT_ARBEIDSFORHOLD_ARBEIDSTAKER_PATH = "/arbeidsforholdinnslag/arbeidstaker/1337"

    @Test
    fun hentArbeidsforhold200() = integrationTest(setupMockedClient()) {
        val client = createClient { install(ContentNegotiation) { json() } }

        val response = get(client, HENT_ARBEIDSFORHOLD_ARBEIDSTAKER_PATH)

        response.status shouldBe HttpStatusCode.OK
    }

    @Test
    fun feilMotAaregSkalGi500() = integrationTest(setupMockedClient(aaregStatus = HttpStatusCode.InternalServerError)) {
        val client = createClient { install(ContentNegotiation) { json() } }

        val response = get(client, HENT_ARBEIDSFORHOLD_ARBEIDSTAKER_PATH)

        response.status shouldBe HttpStatusCode.InternalServerError
    }

    @Test
    fun feilMotEregSkalGi200() = integrationTest(setupMockedClient(eregStatus = HttpStatusCode.InternalServerError)) {
        val client = createClient { install(ContentNegotiation) { json() } }

        val response = get(client, HENT_ARBEIDSFORHOLD_ARBEIDSTAKER_PATH)

        response.status shouldBe HttpStatusCode.OK
    }
}