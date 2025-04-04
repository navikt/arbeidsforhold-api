package no.nav.arbeidsforhold.service.mapper

import no.nav.arbeidsforhold.consumer.aareg.dto.Ansettelsesdetaljer
import no.nav.arbeidsforhold.service.outbound.ArbeidsavtaleDto
import no.nav.arbeidsforhold.service.outbound.PeriodeDto

fun Ansettelsesdetaljer.toOutbound() = ArbeidsavtaleDto(
    ansettelsesform = ansettelsesform?.beskrivelse,
    antallTimerPrUke = antallTimerPrUke,
    arbeidstidsordning = arbeidstidsordning?.beskrivelse,
    sisteStillingsendring = sisteStillingsprosentendring,
    stillingsprosent = avtaltStillingsprosent,
    sisteLoennsendring = sisteLoennsendring,
    yrke = "${yrke?.beskrivelse} (Yrkeskode: ${yrke?.kode})",
    gyldighetsperiode = PeriodeDto(
        periodeFra = rapporteringsmaaneder?.fra,
        periodeTil = rapporteringsmaaneder?.til
    ),
    fartsomraade = fartsomraade?.beskrivelse,
    skipsregister = skipsregister?.beskrivelse,
    skipstype = fartoeystype?.beskrivelse
)
