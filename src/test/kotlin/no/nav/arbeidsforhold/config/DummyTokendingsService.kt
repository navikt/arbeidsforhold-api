package no.nav.arbeidsforhold.config

import no.nav.tms.token.support.tokendings.exchange.TokendingsService

class DummyTokendingsService : TokendingsService {
    override suspend fun exchangeToken(token: String, targetApp: String) = "dummy token"
}