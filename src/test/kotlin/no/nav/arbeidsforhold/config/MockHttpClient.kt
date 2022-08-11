package no.nav.arbeidsforhold.config

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.gson.gson

fun setupMockedClient(success: Boolean = true): HttpClient {
    val aaregResponse = object {}.javaClass.getResource("/__files/arbeidsforhold-list.json")?.readText()!!
    val eregResponse = object {}.javaClass.getResource("/__files/ereg-arbeidsgiver.json")?.readText()!!

    return HttpClient(MockEngine) {
        engine {
            addHandler { request ->
                if (request.url.host == "aareg") {
                    respond(
                        aaregResponse,
                        headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    )
                } else if (request.url.host == "ereg") {
                    respond(
                        eregResponse,
                        headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    )
                } else {
                    respondError(HttpStatusCode.NotFound)
                }
            }

        }
        install(ContentNegotiation) {
            gson()
        }
        expectSuccess = false
    }
}