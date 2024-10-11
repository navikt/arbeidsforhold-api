package no.nav.arbeidsforhold.testdata

import no.nav.arbeidsforhold.consumer.aareg.dto.Ident
import no.nav.arbeidsforhold.consumer.aareg.dto.Identer

val defaultIdenterArbeidstaker = Identer(
    identer = listOf(
        Ident(
            ident = "12345",
            type = "AKTORID"
        ),
        Ident(
            ident = "54321",
            type = "FOLKEREGISTERIDENT"
        )
    )
)

val defaultIdenterArbeidssted = Identer(
    type = "Underenhet",
    identer = listOf(
        Ident(
            ident = "123456789",
            type = "ORGANISASJONSNUMMER"
        )
    )
)

val defaultIdenterOpplysningspliktig = Identer(
    type = "Hovedenhet",
    identer = listOf(
        Ident(
            ident = "987654321",
            type = "ORGANISASJONSNUMMER"
        ),
    )
)