package no.nav.arbeidsforhold.utils

import io.ktor.server.request.ApplicationRequest
import io.ktor.server.request.authorization
import no.nav.security.token.support.core.jwt.JwtToken

private const val PID_CLAIM = "pid"

fun getAuthTokenFromRequest(request: ApplicationRequest): String {
    return requireNotNull(request.authorization()) {"Auth-header er null"}
        .removePrefix("Bearer ")
}

fun getFnrFromToken(token: String): String = JwtToken(token).jwtTokenClaims.getStringClaim(PID_CLAIM)
