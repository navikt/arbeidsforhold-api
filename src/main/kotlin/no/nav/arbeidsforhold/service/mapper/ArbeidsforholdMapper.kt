package no.nav.arbeidsforhold.service.mapper

import no.nav.arbeidsforhold.consumer.aareg.dto.Ansettelsesdetaljer
import no.nav.arbeidsforhold.consumer.aareg.dto.Arbeidsforhold
import no.nav.arbeidsforhold.service.mapper.AnsettelsesperiodeMapper.toOutbound
import no.nav.arbeidsforhold.service.mapper.AntallTimerForTimeloennetMapper.toOutbound
import no.nav.arbeidsforhold.service.mapper.ArbeidsavtaleMapper.toOutbound
import no.nav.arbeidsforhold.service.mapper.ArbeidsgiverMapper.toOutbound
import no.nav.arbeidsforhold.service.mapper.PermisjonPermitteringMapper.joinToSortedList
import no.nav.arbeidsforhold.service.mapper.PermisjonPermitteringMapper.toOutbound
import no.nav.arbeidsforhold.service.mapper.UtenlandsoppholdMapper.toOutbound
import no.nav.arbeidsforhold.service.outbound.ArbeidsforholdDto

object ArbeidsforholdMapper {

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
            yrke = gyldigArbeidsavtale?.yrke,
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
