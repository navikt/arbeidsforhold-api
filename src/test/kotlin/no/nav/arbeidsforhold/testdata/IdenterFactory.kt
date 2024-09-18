package no.nav.arbeidsforhold.testdata

import no.nav.arbeidsforhold.consumer.aareg.dto.Ident
import no.nav.arbeidsforhold.consumer.aareg.dto.Identer

fun createIdenterArbeidstaker() = Identer(
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

fun createIdenterArbeidssted() = Identer(
    type = "Underenhet",
    identer = listOf(
        Ident(
            ident = "123456789",
            type = "ORGANISASJONSNUMMER"
        )
    )
)

fun createIdenterOpplysningspliktig() = Identer(
    type = "Hovedenhet",
    identer = listOf(
        Ident(
            ident = "987654321",
            type = "ORGANISASJONSNUMMER"
        ),
    )
)