package no.nav.arbeidsforhold.services
import no.nav.security.token.support.core.api.ProtectedWithClaims
import no.nav.security.token.support.jaxrs.JaxrsTokenValidationContextHolder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

private const val claimsIssuer = "selvbetjening"

@Component
@Path("/arbeidsforholdinnslag")
@ProtectedWithClaims(issuer = claimsIssuer, claimMap = ["acr=Level4"])
class ArbeidsforholdIdResource @Autowired constructor(private var arbeidsforholdIdService: ArbeidsforholdService) {

    @GET
    @Path("/arbeidstaker/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun hentArbeidsforholdArbeidstaker(@PathParam("id") id: String): Response {

        val fssToken = hentFssToken()
        val fodselsnr = hentFnrFraToken()
        val arbeidsforhold = arbeidsforholdIdService.hentEttArbeidsforholdmedId(fodselsnr, id.toInt(), fssToken)

        return Response
                .ok(arbeidsforhold)
                .build()
    }

    @GET
    @Path("/arbeidsgiver/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun hentArbeidsforholdArbeidsgiver(@HeaderParam("Fnr-Arbeidstaker") fodselsnr: String, @PathParam("id") id: String): Response {

        val fssToken = hentFssToken()
        val arbeidsforhold = arbeidsforholdIdService.hentEttArbeidsforholdmedId(fodselsnr, id.toInt(), fssToken)

        return Response
                .ok(arbeidsforhold)
                .build()
    }

    private fun hentFssToken(): String? {
        return arbeidsforholdIdService.hentFSSToken()
    }

    private fun hentFnrFraToken(): String {
        val context = JaxrsTokenValidationContextHolder.getHolder()
        return context.tokenValidationContext.getClaims(claimsIssuer).getStringClaim("pid")
    }
}
