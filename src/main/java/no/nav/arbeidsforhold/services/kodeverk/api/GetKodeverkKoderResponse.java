package no.nav.arbeidsforhold.services.kodeverk.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetKodeverkKoderResponse {

    private final List<String> koder = new ArrayList<>();

    public GetKodeverkKoderResponse() {
    }

    public List<String> getKoder() {
        return koder;
    }

    public GetKodeverkKoderResponse(List<String> koder) {
        this.koder.addAll(koder);
    }
}