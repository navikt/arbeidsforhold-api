package no.nav.arbeidsforhold.integration

import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import no.nav.arbeidsforhold.config.mocks.setupMockedClient
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test
import kotlin.test.assertNotNull

class HentArbeidsforholdArbeidsgiverIT : IntegrationTest() {

    val HENT_ARBEIDSFORHOLD_ARBEIDSGIVER_PATH = "/arbeidsforholdinnslag/arbeidsgiver/1337"

    @Test
    fun hentArbeidsforhold200() = integrationTest(setupMockedClient()) {
        val client = createClient { install(ContentNegotiation) { json() } }

        val response = get(client, HENT_ARBEIDSFORHOLD_ARBEIDSGIVER_PATH, setFnrHeader = true)

        assertEquals(HttpStatusCode.OK, response.status)
        assertNotNull(response.bodyAsText())
    }

    @Test
    fun feilMotAaregSkalGi500() =
        integrationTest(setupMockedClient(aaregStatus = HttpStatusCode.InternalServerError)) {
            val client = createClient { install(ContentNegotiation) { json() } }

            val response = get(client, HENT_ARBEIDSFORHOLD_ARBEIDSGIVER_PATH, setFnrHeader = true)

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }

    @Test
    fun feilMotEregSkalGi200() =
        integrationTest(setupMockedClient(eregStatus = HttpStatusCode.InternalServerError)) {
            val client = createClient { install(ContentNegotiation) { json() } }

            val response = get(client, HENT_ARBEIDSFORHOLD_ARBEIDSGIVER_PATH, setFnrHeader = true)

            assertEquals(HttpStatusCode.OK, response.status)
        }

    @Test
    fun manglendeFnrHeaderSkalGi400() =
        integrationTest(setupMockedClient(eregStatus = HttpStatusCode.InternalServerError)) {
            val client = createClient { install(ContentNegotiation) { json() } }

            val response = get(client, HENT_ARBEIDSFORHOLD_ARBEIDSGIVER_PATH, setFnrHeader = false)

            assertEquals(HttpStatusCode.BadRequest, response.status)
        }
}