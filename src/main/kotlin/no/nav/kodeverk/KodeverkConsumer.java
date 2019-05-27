package no.nav.kodeverk;

import no.nav.arbeidsforhold.ConsumerFactory;
import no.nav.kodeverk.api.GetKodeverkKoderBetydningerResponse;
import no.nav.kodeverk.exceptions.KodeverkConsumerException;
import no.nav.log.MDCConstants;
import org.slf4j.MDC;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;
import static no.nav.kodeverk.ConsumerFactory.readEntity;

public class KodeverkConsumer {

    private Client client;
    private URI endpoint;
    private static final String SPRAAK = "nb";

    public KodeverkConsumer(Client client, URI endpoint) {
        this.client = client;
        this.endpoint = endpoint;
    }

    public GetKodeverkKoderBetydningerResponse hentKjonn(String kode) {
        Invocation.Builder request = buildKjonnstyperRequest(kode);
        return hentKodeverkBetydning(request);
    }

    public GetKodeverkKoderBetydningerResponse hentKommuner(String kode) {
        Invocation.Builder request = buildKommuneRequest(kode);
        return hentKodeverkBetydning(request);
    }

    public GetKodeverkKoderBetydningerResponse hentLandKoder(String kode) {
        Invocation.Builder request = buildLandkoderRequest(kode);
        return hentKodeverkBetydning(request);
    }

    public GetKodeverkKoderBetydningerResponse hentPersonstatus(String kode) {
        Invocation.Builder request = buildPersonstatusRequest(kode);
        return hentKodeverkBetydning(request);
    }

    public GetKodeverkKoderBetydningerResponse hentPostnummer(String kode) {
        Invocation.Builder request = buildPostnummerRequest(kode);
        return hentKodeverkBetydning(request);
    }

    public GetKodeverkKoderBetydningerResponse hentSivilstand(String kode) {
        Invocation.Builder request = buildSivilstandRequest(kode);
        return hentKodeverkBetydning(request);
    }

    public GetKodeverkKoderBetydningerResponse hentSpraak(String kode) {
        Invocation.Builder request = buildSpraakRequest(kode);
        return hentKodeverkBetydning(request);
    }

    public GetKodeverkKoderBetydningerResponse hentValuta(String kode) {
        Invocation.Builder request = buildValutaRequest(kode);
        return hentKodeverkBetydning(request);
    }

    public GetKodeverkKoderBetydningerResponse hentStatsborgerskap(String kode) {
        Invocation.Builder request = buildStatsborgerskapRequest(kode);
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



    private Invocation.Builder buildKjonnstyperRequest(String kode) {
        return getBuilder(kode, "v1/kodeverk/Kjønnstyper/koder/betydninger", true);

    }

    private Invocation.Builder buildKommuneRequest(String kode) {
        return getBuilder(kode, "v1/kodeverk/Kommuner/koder/betydninger", false);
    }

    private Invocation.Builder buildLandkoderRequest(String kode) {
        return getBuilder(kode, "v1/kodeverk/Landkoder/koder/betydninger", true);
    }

    private Invocation.Builder buildPersonstatusRequest(String kode) {
        return getBuilder(kode, "v1/kodeverk/Personstatuser/koder/betydninger", true);
    }

    private Invocation.Builder buildPostnummerRequest(String kode) {
        return getBuilder(kode, "v1/kodeverk/Postnummer/koder/betydninger", true);
    }

    private Invocation.Builder buildSivilstandRequest(String kode) {
        return getBuilder(kode, "v1/kodeverk/Sivilstander/koder/betydninger", true);
    }

    private Invocation.Builder buildSpraakRequest(String kode) {
        return getBuilder(kode, "v1/kodeverk/Språk/koder/betydninger", true);
    }

    private Invocation.Builder buildValutaRequest(String kode) {
        return getBuilder(kode, "v1/kodeverk/Valutaer/koder/betydninger", true);
    }

    private Invocation.Builder buildStatsborgerskapRequest(String kode) {
        return getBuilder(kode, "v1/kodeverk/StatsborgerskapFreg/koder/betydninger", true);
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

