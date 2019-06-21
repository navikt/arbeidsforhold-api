package no.nav.arbeidsforhold.services

import no.nav.arbeidsforhold.exceptions.ArbeidsforholdConsumerException
import no.nav.security.oidc.api.ProtectedWithClaims
import no.nav.security.oidc.jaxrs.OidcRequestContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

private const val claimsIssuer = "selvbetjening"
private val log = LoggerFactory.getLogger(ArbeidsforholdIdResource::class.java)

@Component
@Path("/arbeidsforholdinnslag/{id}")
@ProtectedWithClaims(issuer = claimsIssuer, claimMap = ["acr=Level4"])
class ArbeidsforholdIdResource @Autowired constructor(private var arbeidsforholdIdService: ArbeidsforholdService) {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun hentPersonalia(@PathParam("id") id: String): Response {
        val fssToken = hentFssToken()
        val fodselsnr = hentFnrFraToken()


        val arbeidsforhold = arbeidsforholdIdService.hentEttArbeidsforholdmedId(fodselsnr, id.toInt(), fssToken)

        return Response
                .ok(arbeidsforhold)
                .build()
    }

    private fun hentFssToken(): String? {
        return arbeidsforholdIdService.hentFSSToken()
    }

    private fun hentFnrFraToken(): String {
        val context = OidcRequestContext.getHolder().oidcValidationContext
        return context.getClaims(claimsIssuer).claimSet.subject
    }


}