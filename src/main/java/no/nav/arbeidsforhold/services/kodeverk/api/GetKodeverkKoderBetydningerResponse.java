package no.nav.arbeidsforhold.services.kodeverk.api;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetKodeverkKoderBetydningerResponse {

    private Map<String, List<Betydning>> betydninger;

    public Map<String, List<Betydning>> getBetydninger() {
        return betydninger;
    }

    public GetKodeverkKoderBetydningerResponse() {
    }

    public void setBetydninger(Map<String, List<Betydning>> betydninger) {
        this.betydninger = new LinkedHashMap<>(betydninger);
    }

}
