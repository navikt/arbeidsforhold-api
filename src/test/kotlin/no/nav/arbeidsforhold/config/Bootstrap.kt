package no.nav.arbeidsforhold.config

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import io.ktor.server.routing.routing
import no.nav.arbeidsforhold.health.health
import no.nav.arbeidsforhold.routes.arbeidsforholdFnr
import no.nav.arbeidsforhold.routes.arbeidsforholdId

fun Application.testModule(appContext: TestApplicationContext) {

    install(ContentNegotiation) {
        json(jsonConfig())
    }

    install(CallLogging) {
        filter { call -> !call.request.path().contains("internal") }
        format { call ->
            val status = call.response.status()
            val httpMethod = call.request.httpMethod.value
            val path = call.request.path()
            "$status - $httpMethod $path"
        }
    }

    routing {
        health(appContext.appMicrometerRegistry)
        arbeidsforholdFnr(appContext.arbeidsforholdService)
        arbeidsforholdId(appContext.arbeidsforholdService)
    }
}