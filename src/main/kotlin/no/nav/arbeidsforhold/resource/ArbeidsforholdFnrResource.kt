package no.nav.arbeidsforhold.resource

import no.nav.arbeidsforhold.service.ArbeidsforholdService
import no.nav.arbeidsforhold.util.hentFnrFraToken
import no.nav.security.token.support.core.api.ProtectedWithClaims
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

private const val claimsIssuer = "selvbetjening"

@Component
@Path("/")
@ProtectedWithClaims(issuer = claimsIssuer, claimMap = ["acr=Level4"])
class ArbeidsforholdFnrResource @Autowired constructor(private var arbeidsforholdService: ArbeidsforholdService) {

    @GET
    @Path("/arbeidsforhold")
    @Produces(MediaType.APPLICATION_JSON)
    fun hentArbeidsforhold(): Response {
        val fodselsnr = hentFnrFraToken()
        val arbeidsforhold = arbeidsforholdService.hentArbeidsforhold(fodselsnr)

        return Response
            .ok(arbeidsforhold)
            .build()
    }
}
