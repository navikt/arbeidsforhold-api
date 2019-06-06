package no.nav.arbeidsforhold.services.kodeverk;

import no.nav.arbeidsforhold.ConsumerFactory;
import no.nav.arbeidsforhold.services.kodeverk.api.GetKodeverkKoderBetydningerResponse;
import no.nav.arbeidsforhold.services.kodeverk.exceptions.KodeverkConsumerException;
import no.nav.log.MDCConstants;
import org.slf4j.MDC;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;
import static no.nav.arbeidsforhold.ConsumerFactory.readEntity;

public class KodeverkConsumer {

    private Client client;
    private URI endpoint;
    private static final String SPRAAK = "nb";

    public KodeverkConsumer(Client client, URI endpoint) {
        this.client = client;
        this.endpoint = endpoint;
    }

    public GetKodeverkKoderBetydningerResponse hentYrke(String kode) {
        Invocation.Builder request = buildYrkeRequest(kode);
        return hentKodeverkBetydning(request);
    }

    public GetKodeverkKoderBetydningerResponse hentArbeidsforholdstyper(String kode) {
        Invocation.Builder request = buildArbeidsforholdstyperRequest(kode);
        return hentKodeverkBetydning(request);
    }

    public GetKodeverkKoderBetydningerResponse hentArbeidstidsordningstyper(String kode) {
        Invocation.Builder request = buildArbeidstidsordningsRequest(kode);
        return hentKodeverkBetydning(request);
    }


    public GetKodeverkKoderBetydningerResponse hentFartsomraade(String kode) {
        Invocation.Builder request = buildFartsomraadeRequest(kode);
        return hentKodeverkBetydning(request);
    }


    public GetKodeverkKoderBetydningerResponse hentSkipsregister(String kode) {
        Invocation.Builder request = buildSkipsregisterRequest(kode);
        return hentKodeverkBetydning(request);
    }

    public GetKodeverkKoderBetydningerResponse hentSkipstyper(String kode) {
        Invocation.Builder request = buildSkipstyperRequest(kode);
        return hentKodeverkBetydning(request);
    }

    public GetKodeverkKoderBetydningerResponse hentLand(String kode) {
        Invocation.Builder request = buildLandRequest(kode);
        return hentKodeverkBetydning(request);
    }

    private Invocation.Builder getBuilder(String kode, String path, Boolean eksluderUgyldige) {
        return client.target(endpoint)
                .path(path)
                .queryParam("spraak", SPRAAK)
                .queryParam("ekskluderUgyldige", eksluderUgyldige)
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", ConsumerFactory.CONSUMER_ID)
                .header("Nav-Personident", kode);
    }

    private Invocation.Builder buildYrkeRequest(String kode) {
        return getBuilder(kode, "v1/kodeverk/Yrker/koder/betydninger", false);
    }

    private Invocation.Builder buildArbeidsforholdstyperRequest(String kode) {
        return getBuilder(kode, "v1/kodeverk/Arbeidsforholdstyper/koder/betydninger", false);
    }

    private Invocation.Builder buildArbeidstidsordningsRequest(String kode) {
        return getBuilder(kode, "v1/kodeverk/Arbeidstidsordninger/koder/betydninger", false);
    }
    private Invocation.Builder buildFartsomraadeRequest(String kode) {
        return getBuilder(kode, "v1/kodeverk/Fartsomraade/koder/betydninger", false);
    }

    private Invocation.Builder buildSkipsregisterRequest(String kode) {
        return getBuilder(kode, "v1/kodeverk/Skipsregister/koder/betydninger", false);
    }

    private Invocation.Builder buildSkipstyperRequest(String kode) {
        return getBuilder(kode, "v1/kodeverk/Skipstyper/koder/betydninger", false);
    }

    private Invocation.Builder buildLandRequest(String kode) {
        return getBuilder(kode, "v1/kodeverk/Landkoder/koder/betydninger", false);
    }

    private GetKodeverkKoderBetydningerResponse hentKodeverkBetydning(Invocation.Builder request) {
        try (Response response = request.get()) {
            return readResponseBetydning(response);
        } catch (KodeverkConsumerException e) {
            throw e;
        } catch (Exception e) {
            String msg = "Forsøkte å konsumere kodeverk. endpoint=[" + endpoint + "].";
            throw new KodeverkConsumerException(msg, e);
        }
    }

    private GetKodeverkKoderBetydningerResponse readResponseBetydning(Response r) {
        if (!SUCCESSFUL.equals(r.getStatusInfo().getFamily())) {
            String msg = "Forsøkte å konsumere kodeverk. endpoint=[" + endpoint + "], HTTP response status=[" + r.getStatus() + "].";
            throw new KodeverkConsumerException(msg + " - " + readEntity(String.class, r));
        } else {
            return readEntity(GetKodeverkKoderBetydningerResponse.class, r);
        }
    }

}

