package no.nav.arbeidsforhold.config.mocks

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respondError
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import no.nav.arbeidsforhold.config.jsonConfig


fun setupMockedClient(
    aaregStatus: HttpStatusCode = HttpStatusCode.OK,
    eregStatus: HttpStatusCode = HttpStatusCode.OK
): HttpClient {
    val AAREG = "aareg"
    val EREG = "ereg"

    return HttpClient(MockEngine) {
        engine {
            addHandler { request ->
                when (request.url.host) {
                    AAREG -> {
                        mockAareg(request, aaregStatus)
                    }
                    EREG -> {
                        mockEreg(request, eregStatus)
                    }
                    else -> {
                        respondError(HttpStatusCode.NotFound)
                    }
                }
            }

        }
        install(ContentNegotiation) {
            json(jsonConfig())
        }
        install(HttpTimeout)
        expectSuccess = false
    }
}

fun readJson(name: String): String {
    return object {}.javaClass.getResource(name)?.readText()!!
}