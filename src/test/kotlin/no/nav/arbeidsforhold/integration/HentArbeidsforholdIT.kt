package no.nav.arbeidsforhold.integration

import io.kotest.assertions.json.shouldEqualJson
import io.kotest.matchers.shouldBe
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import no.nav.arbeidsforhold.config.setupMockedClient
import no.nav.arbeidsforhold.testutils.readJsonFile
import kotlin.test.Test

class HentArbeidsforholdIT : IntegrationTest() {

    @Test
    fun hentArbeidsforhold200() = integrationTest(setupMockedClient()) {
        val response = get(HENT_ARBEIDSFORHOLD_FNR_PATH)

        response.status shouldBe HttpStatusCode.OK
        response.bodyAsText() shouldEqualJson readJsonFile("/json/expected-response/arbeidsforhold-list.json")
    }

    @Test
    fun feilMotAaregSkalGi500() = integrationTest(setupMockedClient(aaregStatus = HttpStatusCode.InternalServerError)) {
        val response = get(HENT_ARBEIDSFORHOLD_FNR_PATH)

        response.status shouldBe HttpStatusCode.InternalServerError
    }

    @Test
    fun feilMotEregSkalGi200() = integrationTest(setupMockedClient(eregStatus = HttpStatusCode.InternalServerError)) {
        val response = get(HENT_ARBEIDSFORHOLD_FNR_PATH)

        response.status shouldBe HttpStatusCode.OK
        response.bodyAsText() shouldEqualJson readJsonFile("/json/expected-response/arbeidsforhold-list-with-ereg-error.json")
    }

    companion object {
        private const val HENT_ARBEIDSFORHOLD_FNR_PATH = "/arbeidsforhold"
    }
}