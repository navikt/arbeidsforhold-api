package no.nav.arbeidsforhold.integration

import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.gson.gson
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class HentArbeidsforholdArbeidsgiverIT : IntegrationTest() {

    @Test
    fun testHentArbeidsforhold200() = integrationTest {
        val client = createClient { install(ContentNegotiation) { gson() } }

        val response = get(client, "/arbeidsforhold")

        assertEquals(HttpStatusCode.OK, response.status)
    }


}