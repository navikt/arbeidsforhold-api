package no.nav.arbeidsforhold.config.mocks

import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import no.nav.arbeidsforhold.testutils.contentTypeJsonHeader
import no.nav.arbeidsforhold.testutils.readJsonFile


fun MockRequestHandleScope.mockAareg(request: HttpRequestData, status: HttpStatusCode): HttpResponseData {
    return if (status.isSuccess()) {
        respond(
            content = readAaregResponse(request.url.encodedPath),
            headers = contentTypeJsonHeader(),
        )
    } else {
        respondError(status)
    }
}

private fun readAaregResponse(path: String) = when {
    path.startsWith("/api/v2/arbeidstaker") -> readJsonFile("/json/mocks/arbeidsforhold-list.json")
    path.startsWith("/api/v2/arbeidsforhold") -> readJsonFile("/json/mocks/arbeidsforhold-single.json")
    else -> throw IllegalArgumentException("Fant ikke mock for path")
}
