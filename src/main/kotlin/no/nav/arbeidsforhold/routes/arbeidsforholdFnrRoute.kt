package no.nav.arbeidsforhold.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import no.nav.arbeidsforhold.service.ArbeidsforholdService
import no.nav.arbeidsforhold.util.getFnrFromToken
import no.nav.arbeidsforhold.util.getSelvbetjeningTokenFromCall
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("arbeidsforholdFnrRoute")

fun Route.arbeidsforholdFnr(arbeidsforholdService: ArbeidsforholdService) {
    get("/arbeidsforhold") {
        try {
            val selvbetjeningIdtoken = getSelvbetjeningTokenFromCall(call)
            val fnr = getFnrFromToken(selvbetjeningIdtoken)

            call.respond(arbeidsforholdService.hentArbeidsforhold(selvbetjeningIdtoken, fnr))
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved henting av arbeidsforhold", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }
}