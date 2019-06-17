package no.nav.arbeidsforhold.dto.transformer

import no.nav.arbeidsforhold.domain.Arbeidsforhold
import no.nav.arbeidsforhold.dto.outbound.ArbeidsforholdDto
import no.nav.personopplysninger.features.arbeidsforhold.dto.transformer.OpplysningspliktigArbeidsgiverTransformer

object ArbeidsforholdTransformer {

    fun toOutbound(inbound: Arbeidsforhold, arbgivnavn: String?, opplarbgivnavn: String?) = ArbeidsforholdDto(

            navArbeidsforholdId = inbound.navArbeidsforholdId,
            arbeidsgiver = ArbeidsgiverTransformer.toOutbound(inbound.arbeidsgiver, arbgivnavn),
            opplysningspliktigarbeidsgiver = OpplysningspliktigArbeidsgiverTransformer.toOutbound(inbound.opplysningspliktig, opplarbgivnavn),
            ansettelsesperiode = PeriodeTransformer.toOutboundfromAnsettelsesperiode(inbound.ansettelsesperiode),
            utenlandsopphold = UtenlandsoppholdTransformer.toOutboundArray(inbound.utenlandsopphold),
            permisjonPermittering = PermisjonPermitteringTransformer.toOutboundArray(inbound.permisjonPermitteringer)

    )
}
