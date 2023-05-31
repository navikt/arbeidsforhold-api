package no.nav.arbeidsforhold.util

import io.ktor.server.request.ApplicationRequest
import io.ktor.server.request.authorization
import no.nav.security.token.support.core.jwt.JwtToken

private const val PID_CLAIM = "pid"

fun getAuthTokenFromRequest(request: ApplicationRequest): String {
    return request.authorization()?.replace("Bearer ", "")
        ?: throw RuntimeException("Kunne ikke utlede token fra request")
}

fun getFnrFromToken(token: String): String {
    return JwtToken(token).jwtTokenClaims.getStringClaim(PID_CLAIM)
}