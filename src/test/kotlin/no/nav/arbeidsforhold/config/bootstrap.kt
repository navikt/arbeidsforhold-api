package no.nav.arbeidsforhold.config

import io.ktor.serialization.gson.gson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing
import no.nav.arbeidsforhold.routes.arbeidsforholdFnr
import no.nav.arbeidsforhold.routes.arbeidsforholdId


fun Application.testModule(appContext: TestApplicationContext) {

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    routing {
        arbeidsforholdFnr(appContext.arbeidsforholdService)
        arbeidsforholdId(appContext.arbeidsforholdService)
    }
}