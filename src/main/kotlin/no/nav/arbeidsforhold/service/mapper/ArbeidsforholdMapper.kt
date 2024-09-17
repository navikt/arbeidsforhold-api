package no.nav.arbeidsforhold.service.mapper

import no.nav.arbeidsforhold.consumer.aareg.dto.Arbeidsforhold
import no.nav.arbeidsforhold.service.outbound.ArbeidsforholdDto

fun Arbeidsforhold.toOutbound(
    arbeidsgiverNavn: String?,
    opplysningspliktigNavn: String?
) = ArbeidsforholdDto(
    navArbeidsforholdId = navArbeidsforholdId,
    eksternArbeidsforholdId = id,
    yrke = ansettelsesdetaljer.firstOrNull { it.rapporteringsmaaneder?.til == null }?.yrke?.beskrivelse,
    arbeidsgiver = arbeidssted?.toOutbound(arbeidsgiverNavn),
    opplysningspliktigarbeidsgiver = opplysningspliktig?.toOutbound(opplysningspliktigNavn),
    ansettelsesperiode = ansettelsesperiode?.toOutbound(),
    utenlandsopphold = utenlandsopphold.map { it.toOutbound() },
    permisjonPermittering = joinToSortedList(permisjoner, permitteringer).map { it.toOutbound() }
)

fun Arbeidsforhold.toOutboundDetaljert(
    arbeidsgiverNavn: String?,
    opplysningspliktigNavn: String?
): ArbeidsforholdDto {
    val arbeidsavtaler = ansettelsesdetaljer.map { it.toOutbound() }
    val gyldigArbeidsavtale = arbeidsavtaler.firstOrNull { it.gyldighetsperiode?.periodeTil == null }

    return this.toOutbound(arbeidsgiverNavn, opplysningspliktigNavn).copy(
        yrke = gyldigArbeidsavtale?.yrke, // Bruker mappet verdi for å få med yrkeskode
        type = type?.beskrivelse,
        sistBekreftet = sistBekreftet,
        arbeidsavtaler = when {
            arbeidsavtaler.size > 1 -> arbeidsavtaler
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
