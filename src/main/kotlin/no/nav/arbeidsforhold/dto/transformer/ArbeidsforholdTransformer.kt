package no.nav.arbeidsforhold.dto.transformer

import no.nav.arbeidsforhold.domain.Arbeidsforhold
import no.nav.arbeidsforhold.dto.outbound.ArbeidsforholdDto
import no.nav.personopplysninger.features.arbeidsforhold.dto.transformer.OpplysningspliktigArbeidsgiverTransformer

object ArbeidsforholdTransformer {

    fun toOutbound(inbound: Arbeidsforhold, arbgivnavn: String?, opplarbgivnavn: String?, yrke: String?) = ArbeidsforholdDto(

            navArbeidsforholdId = inbound.navArbeidsforholdId,
            eksternArbeidsforholdId = inbound.arbeidsforholdId,
            yrke = yrke,
            arbeidsgiver = ArbeidsgiverTransformer.toOutbound(inbound.arbeidsgiver, arbgivnavn),
            opplysningspliktigarbeidsgiver = OpplysningspliktigArbeidsgiverTransformer.toOutbound(inbound.opplysningspliktig, opplarbgivnavn),
            ansettelsesperiode = AnsettelsesperiodeTransformer.toOutbound(inbound.ansettelsesperiode),
            utenlandsopphold = UtenlandsoppholdTransformer.toOutboundArray(inbound.utenlandsopphold),
            permisjonPermittering = PermisjonPermitteringTransformer.toOutboundArray(inbound.permisjonPermitteringer)

    )
}
