package no.nav.arbeidsforhold

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.mockk.coEvery
import io.mockk.mockkStatic
import no.nav.arbeidsforhold.config.TestApplicationContext
import no.nav.arbeidsforhold.config.mocks.setupMockedClient
import no.nav.arbeidsforhold.config.testModule
import no.nav.arbeidsforhold.util.getAuthTokenFromRequest
import no.nav.arbeidsforhold.util.getFnrFromToken

fun main() {
    embeddedServer(Netty, port = 8080, watchPaths = listOf("classes")) {
        mockkStatic(::getAuthTokenFromRequest)
        mockkStatic(::getFnrFromToken)

        coEvery { getAuthTokenFromRequest(any()) } returns "dummyToken"
        coEvery { getFnrFromToken(any()) } returns "10108000398"

        testModule(TestApplicationContext(setupMockedClient()))
    }.start(wait = true)
}