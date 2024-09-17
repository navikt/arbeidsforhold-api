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


fun MockRequestHandleScope.mockEreg(request: HttpRequestData, status: HttpStatusCode): HttpResponseData {
    return if (status.isSuccess()) {
        respond(
            content = readEregResponse(request.url.encodedPath),
            headers = contentTypeJsonHeader(),
        )
    } else {
        respondError(status)
    }
}

private fun readEregResponse(path: String) = when {
    path.startsWith("/v2/organisasjon/911742233") -> readJsonFile("/json/mocks/ereg-arbeidsgiver.json")
    path.startsWith("/v2/organisasjon/912783936") -> readJsonFile("/json/mocks/ereg-opplysningspliktig.json")
    else -> throw IllegalArgumentException("Fant ikke mock for path")
}

