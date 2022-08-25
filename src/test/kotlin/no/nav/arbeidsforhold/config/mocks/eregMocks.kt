package no.nav.arbeidsforhold.config.mocks

import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.request.HttpRequestData
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.http.isSuccess


fun MockRequestHandleScope.mockEreg(request: HttpRequestData, status: HttpStatusCode) =
    if (status.isSuccess()) {
        respond(
            readEregResponse(request.url.encodedPath),
            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        )
    } else {
        respondError(status)
    }

private fun readEregResponse(path: String): String {
    return if (path.startsWith("/v1/organisasjon/911742233")) {
        readJson("/json/ereg-arbeidsgiver.json")
    } else if (path.startsWith("/v1/organisasjon/912783936")) {
        readJson("/json/ereg-opplysningspliktig.json")
    } else {
        throw RuntimeException("Fant ikke mock for path")
    }
}

