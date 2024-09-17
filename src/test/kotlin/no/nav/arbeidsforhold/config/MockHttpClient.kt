package no.nav.arbeidsforhold.config

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respondError
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import no.nav.arbeidsforhold.config.mocks.mockAareg
import no.nav.arbeidsforhold.config.mocks.mockEreg

private const val AAREG = "aareg"
private const val EREG = "ereg"

fun setupMockedClient(
    aaregStatus: HttpStatusCode = HttpStatusCode.OK,
    eregStatus: HttpStatusCode = HttpStatusCode.OK
): HttpClient {
    return HttpClient(MockEngine) {
        engine {
            addHandler { request ->
                when (request.url.host) {
                    AAREG -> mockAareg(request, aaregStatus)
                    EREG -> mockEreg(request, eregStatus)
                    else -> respondError(HttpStatusCode.NotFound)
                }
            }

        }
        install(ContentNegotiation) {
            json(jsonConfig())
        }
        install(HttpTimeout)
    }
}