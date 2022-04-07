package no.nav.arbeidsforhold.services.kodeverk.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetKodeverkResponse {

    private List<String> kodeverksnavn;

    public List<String> getKodeverksnavn() {
        return kodeverksnavn;
    }

    public GetKodeverkResponse() {
    }

    public GetKodeverkResponse(List<String> kodeverksnavn) {
        this.kodeverksnavn = kodeverksnavn;
    }
}
