package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.dto.Ansettelsesdetaljer
import no.nav.arbeidsforhold.consumer.aareg.dto.Arbeidsforhold
import no.nav.arbeidsforhold.service.outbound.ArbeidsforholdDto
import no.nav.arbeidsforhold.service.transformer.AnsettelsesperiodeTransformer.toOutbound
import no.nav.arbeidsforhold.service.transformer.AntallTimerForTimeloennetTransformer.toOutbound
import no.nav.arbeidsforhold.service.transformer.ArbeidsavtaleTransformer.toOutbound
import no.nav.arbeidsforhold.service.transformer.ArbeidsgiverTransformer.toOutbound
import no.nav.arbeidsforhold.service.transformer.PermisjonPermitteringTransformer.joinToSortedList
import no.nav.arbeidsforhold.service.transformer.PermisjonPermitteringTransformer.toOutbound
import no.nav.arbeidsforhold.service.transformer.UtenlandsoppholdTransformer.toOutbound

object ArbeidsforholdTransformer {

    fun Arbeidsforhold.toOutbound(
        arbeidsgiverNavn: String?,
        opplysningspliktigNavn: String?,
    ): ArbeidsforholdDto {
        return ArbeidsforholdDto(
            navArbeidsforholdId = navArbeidsforholdId,
            eksternArbeidsforholdId = id,
            yrke = ansettelsesdetaljer.gyldigArbeidsavtale()?.yrke?.beskrivelse,
            arbeidsgiver = arbeidssted.toOutbound(arbeidsgiverNavn),
            opplysningspliktigarbeidsgiver = opplysningspliktig.toOutbound(opplysningspliktigNavn),
            ansettelsesperiode = ansettelsesperiode.toOutbound(),
            utenlandsopphold = utenlandsopphold.map { it.toOutbound() },
            permisjonPermittering = joinToSortedList(permisjoner, permitteringer).map { it.toOutbound() }
        )
    }

    fun Arbeidsforhold.toOutboundDetaljert(
        arbeidsgiverNavn: String?,
        opplysningspliktigNavn: String?
    ): ArbeidsforholdDto {
        val gyldigArbeidsavtale = ansettelsesdetaljer.gyldigArbeidsavtale()?.toOutbound()

        return this.toOutbound(arbeidsgiverNavn, opplysningspliktigNavn).copy(
            type = type?.beskrivelse,
            sistBekreftet = sistBekreftet,
            arbeidsavtaler = when {
                ansettelsesdetaljer.size > 1 -> ansettelsesdetaljer.map { it.toOutbound() }
                else -> emptyList()
            },
            ansettelsesform = gyldigArbeidsavtale?.ansettelsesform,
            antallTimerPrUke = gyldigArbeidsavtale?.antallTimerPrUke,
            stillingsprosent = gyldigArbeidsavtale?.stillingsprosent,
            arbeidstidsordning = gyldigArbeidsavtale?.arbeidstidsordning,
            sisteStillingsendring = gyldigArbeidsavtale?.sisteStillingsendring,
            sisteLoennsendring = gyldigArbeidsavtale?.sisteLoennsendring,
            fartsomraade = gyldigArbeidsavtale?.fartsomraade,
            skipsregister = gyldigArbeidsavtale?.skipsregister,
            skipstype = gyldigArbeidsavtale?.skipstype,
            antallTimerForTimelonnet = timerMedTimeloenn.map { it.toOutbound() }
        )
    }

    private fun List<Ansettelsesdetaljer>.gyldigArbeidsavtale() = firstOrNull { it.rapporteringsmaaneder?.til == null }
}
