package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.dto.Arbeidsforhold
import no.nav.arbeidsforhold.service.outbound.ArbeidsforholdDto
import no.nav.arbeidsforhold.service.transformer.AnsettelsesperiodeTransformer.toOutbound
import no.nav.arbeidsforhold.service.transformer.ArbeidsgiverTransformer.toOutbound
import no.nav.arbeidsforhold.service.transformer.PermisjonPermitteringTransformer.toOutboundArray
import no.nav.arbeidsforhold.service.transformer.UtenlandsoppholdTransformer.toOutboundArray

object ArbeidsforholdTransformer {

    fun Arbeidsforhold.toOutbound(arbgivnavn: String?, opplarbgivnavn: String?, yrke: String?): ArbeidsforholdDto {
        return ArbeidsforholdDto(
            navArbeidsforholdId = navArbeidsforholdId,
            eksternArbeidsforholdId = id,
            yrke = yrke,
            arbeidsgiver = arbeidssted.toOutbound(arbgivnavn),
            opplysningspliktigarbeidsgiver = opplysningspliktig.toOutbound(opplarbgivnavn),
            ansettelsesperiode = ansettelsesperiode.toOutbound(),
            utenlandsopphold = utenlandsopphold.toOutboundArray(),
            permisjonPermittering = permisjoner.plus(permitteringer).toOutboundArray()
        )
    }
}
