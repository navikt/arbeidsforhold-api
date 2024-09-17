package no.nav.arbeidsforhold.testdata

import no.nav.arbeidsforhold.consumer.aareg.dto.Arbeidsforhold
import no.nav.arbeidsforhold.consumer.aareg.dto.Kodeverksentitet
import no.nav.arbeidsforhold.testdata.AnsettelsesdetaljerFactory.createAnsettelsesdetaljer
import no.nav.arbeidsforhold.testdata.AnsettelsesperiodeFactory.createAnsettelsesperiode
import no.nav.arbeidsforhold.testdata.IdenterFactory.createIdenterArbeidssted
import no.nav.arbeidsforhold.testdata.IdenterFactory.createIdenterArbeidstaker
import no.nav.arbeidsforhold.testdata.IdenterFactory.createIdenterOpplysningspliktig
import no.nav.arbeidsforhold.testdata.PermisjonPermitteringFactory.createPermisjonPermittering
import no.nav.arbeidsforhold.testdata.TimerMedTimeloennFactory.createTimerMedTimeloenn
import no.nav.arbeidsforhold.testdata.UtenlandsoppholdFactory.createUtenlandsopphold


object ArbeidsforholdFactory {

    fun createArbeidsforhold() = Arbeidsforhold(
        ansettelsesperiode = createAnsettelsesperiode(),
        timerMedTimeloenn = listOf(createTimerMedTimeloenn()),
        ansettelsesdetaljer = listOf(createAnsettelsesdetaljer()),
        id = "1111",
        arbeidssted = createIdenterArbeidssted(),
        arbeidstaker = createIdenterArbeidstaker(),
        navArbeidsforholdId = 1111,
        opplysningspliktig = createIdenterOpplysningspliktig(),
        permisjoner = listOf(createPermisjonPermittering()),
        permitteringer = listOf(createPermisjonPermittering()),
        sistBekreftet = "01.01.2016",
        type = Kodeverksentitet(
            kode = "type kode",
            beskrivelse = "type beskrivelse"
        ),
        utenlandsopphold = listOf(createUtenlandsopphold())
    )
}