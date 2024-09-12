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
import no.nav.arbeidsforhold.testutils.JsonUtils.readJsonFile


fun MockRequestHandleScope.mockAareg(request: HttpRequestData, status: HttpStatusCode) =
    if (status.isSuccess()) {
        respond(
            readAaregResponse(request.url.encodedPath),
            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        )
    } else {
        respondError(status)
    }

private fun readAaregResponse(path: String): String {
    return if (path.startsWith("/api/v2/arbeidstaker")) {
        readJsonFile("/json/mocks/arbeidsforhold-list.json")
    } else if (path.startsWith("/api/v2/arbeidsforhold")) {
        readJsonFile("/json/mocks/arbeidsforhold-single.json")
    } else {
        throw RuntimeException("Fant ikke mock for path")
    }
}