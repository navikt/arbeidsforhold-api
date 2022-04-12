package no.nav.arbeidsforhold.dto

import no.nav.arbeidsforhold.service.outbound.ArbeidsavtaleDto
import no.nav.arbeidsforhold.service.outbound.PeriodeDto
import no.nav.arbeidsforhold.util.DtoUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DtoUtilsTest {

    @Test
    fun testHentSisteArbeidsavtale() {
        val avtaler = ArrayList<ArbeidsavtaleDto>()
        avtaler.add(
            ArbeidsavtaleDto(
                gyldighetsperiode = PeriodeDto(
                    periodeFra = "2018-05-20"
                ),
                yrke = "Kokk"
            )
        )
        avtaler.add(
            ArbeidsavtaleDto(
                gyldighetsperiode = PeriodeDto(
                    periodeFra = "2019-02-10"
                ),
                yrke = "Mekaniker"
            )
        )
        avtaler.add(
            ArbeidsavtaleDto(
                gyldighetsperiode = PeriodeDto(
                    periodeFra = "2017-10-30"
                ),
                yrke = "Pilot"
            )
        )
        assertEquals("Mekaniker", DtoUtils.hentYrkeForSisteArbeidsavtale(avtaler))

    }
}