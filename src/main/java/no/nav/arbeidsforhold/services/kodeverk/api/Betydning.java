package no.nav.arbeidsforhold.services.kodeverk.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Betydning {

    private LocalDate gyldigFra;
    private LocalDate gyldigTil;
    private Map<String, Beskrivelse> beskrivelser;

    public Map<String, Beskrivelse> getBeskrivelser() {
        if (beskrivelser == null) {
            beskrivelser = new HashMap<>();
        }
        return beskrivelser;
    }
}
