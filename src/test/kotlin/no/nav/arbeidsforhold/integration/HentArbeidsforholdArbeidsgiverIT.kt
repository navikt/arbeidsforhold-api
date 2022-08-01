package no.nav.arbeidsforhold.integration

import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import no.nav.arbeidsforhold.config.mainModule
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class HentArbeidsforholdArbeidsgiverIT {

    @Test
    fun testytesty() = testApplication {
        application {
            mainModule()
        }
        val response = client.get("/arbeidsforhold")
        assertEquals(HttpStatusCode.OK, response.status)
    }

}