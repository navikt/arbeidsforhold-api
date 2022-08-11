package no.nav.arbeidsforhold.config.mock

import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.request.HttpRequestData
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.http.isSuccess


fun MockRequestHandleScope.mockAareg(request: HttpRequestData, status: HttpStatusCode) =
    if (status.isSuccess()) {
        respond(
            readAaregResponse(request.url.encodedPath),
            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        )
    } else {
        respondError(HttpStatusCode.InternalServerError)
    }

private fun readAaregResponse(path: String): String {
    return if (path.startsWith("/api/v2/arbeidstaker")) {
        readJson("/json/arbeidsforhold-list.json")
    } else if (path.startsWith("/api/v2/arbeidsforhold")) {
        readJson("/json/arbeidsforhold-single.json")
    } else {
        throw RuntimeException("Fant ikke mock for path")
    }
}