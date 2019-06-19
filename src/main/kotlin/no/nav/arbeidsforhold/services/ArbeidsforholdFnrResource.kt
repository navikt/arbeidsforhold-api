package no.nav.arbeidsforhold.services

import no.nav.arbeidsforhold.exceptions.ArbeidsforholdConsumerException
import no.nav.security.oidc.api.ProtectedWithClaims
import no.nav.security.oidc.jaxrs.OidcRequestContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

private const val claimsIssuer = "selvbetjening"
private val log = LoggerFactory.getLogger(ArbeidsforholdFnrResource::class.java)

@Component
@Path("/")
@ProtectedWithClaims(issuer = claimsIssuer, claimMap = ["acr=Level4"])
class ArbeidsforholdFnrResource @Autowired constructor(private var arbeidsforholdService: ArbeidsforholdService) {

    @GET
    @Path("/arbeidsforhold")
    @Produces(MediaType.APPLICATION_JSON)
    fun hentArbeidsforhold(): Response {
        val fssToken = hentFssToken()
        val fodselsnr = hentFnrFraToken()
        var arbeidsforholdresponse = false
        while (!arbeidsforholdresponse) {
            try {
                val arbeidsforhold = arbeidsforholdService.hentArbeidsforhold(fodselsnr, fssToken)
                arbeidsforholdresponse = true
                return Response
                        .ok(arbeidsforhold)
                        .build()
            } catch (ae: ArbeidsforholdConsumerException) {
                log.warn("Failed response from arbeidsforhold " + fssToken);
            }
        }

        return Response
                .ok()
                .build()
    }

    private fun hentFssToken(): String? {
        return arbeidsforholdService.hentFSSToken()
    }

    private fun hentFnrFraToken(): String {
        val context = OidcRequestContext.getHolder().oidcValidationContext
        return context.getClaims(claimsIssuer).claimSet.subject
    }
}