package no.nav.arbeidsforhold.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import no.nav.arbeidsforhold.config.FNR_ARBEIDSTAKER
import no.nav.arbeidsforhold.service.ArbeidsforholdService
import no.nav.arbeidsforhold.util.getAuthTokenFromRequest
import no.nav.arbeidsforhold.util.getFnrFromToken
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("arbeidsforholdIdRoute")

fun Route.arbeidsforholdId(arbeidsforholdService: ArbeidsforholdService) {
    route("/arbeidsforholdinnslag") {
        get("/arbeidstaker/{id}") {
            try {
                val authToken = getAuthTokenFromRequest(call.request)
                val fnr = getFnrFromToken(authToken)
                val id = call.parameters["id"]!!.toInt()

                call.respond(arbeidsforholdService.hentEttArbeidsforholdmedId(authToken, fnr, id))
            } catch (e: Exception) {
                logger.error("Noe gikk galt ved henting av arbeidsforhold", e)
                call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
            }
        }

        get("/arbeidsgiver/{id}") {
            try {
                val authToken = getAuthTokenFromRequest(call.request)
                val fnr = call.request.headers[FNR_ARBEIDSTAKER]
                val id = call.parameters["id"]!!.toInt()

                if (fnr.isNullOrEmpty()) {
                    call.respond(HttpStatusCode.BadRequest, "Mangler header $FNR_ARBEIDSTAKER")
                } else {
                    call.respond(arbeidsforholdService.hentEttArbeidsforholdmedId(authToken, fnr, id))
                }
            } catch (e: Exception) {
                logger.error("Noe gikk galt ved henting av arbeidsforhold", e)
                call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
            }
        }
    }
}