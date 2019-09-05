package no.nav.arbeidsforhold.services.kodeverk;

import no.nav.arbeidsforhold.ConsumerFactory;
import no.nav.arbeidsforhold.services.kodeverk.api.GetKodeverkKoderBetydningerResponse;
import no.nav.arbeidsforhold.services.kodeverk.exceptions.KodeverkConsumerException;
import no.nav.log.MDCConstants;
import org.slf4j.MDC;
import org.springframework.cache.annotation.Cacheable;

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

    @Cacheable("yrker")
    public GetKodeverkKoderBetydningerResponse hentYrke() {
        return hentKodeverkBetydning(getBuilder("v1/kodeverk/Yrker/koder/betydninger", false));
    }

    @Cacheable("arbeidsforholdstyper")
    public GetKodeverkKoderBetydningerResponse hentArbeidsforholdstyper() {
        return hentKodeverkBetydning(getBuilder("v1/kodeverk/Arbeidsforholdstyper/koder/betydninger", false));
    }

    @Cacheable("arbeidstidsordninger")
    public GetKodeverkKoderBetydningerResponse hentArbeidstidsordningstyper() {
        return hentKodeverkBetydning(getBuilder("v1/kodeverk/Arbeidstidsordninger/koder/betydninger", false));
    }

    @Cacheable("fartsomraader")
    public GetKodeverkKoderBetydningerResponse hentFartsomraade() {
        return hentKodeverkBetydning(getBuilder("v1/kodeverk/Fartsområder/koder/betydninger", false));
    }

    @Cacheable("skipsregistre")
    public GetKodeverkKoderBetydningerResponse hentSkipsregister() {
        return hentKodeverkBetydning(getBuilder("v1/kodeverk/Skipsregistre/koder/betydninger", false));
    }

    @Cacheable("skipstyper")
    public GetKodeverkKoderBetydningerResponse hentSkipstyper() {
        return hentKodeverkBetydning(getBuilder("v1/kodeverk/Skipstyper/koder/betydninger", false));
    }

    @Cacheable("land")
    public GetKodeverkKoderBetydningerResponse hentLand() {
        return hentKodeverkBetydning(getBuilder("v1/kodeverk/LandkoderISO2/koder/betydninger", false));
    }

    @Cacheable("permisjonstyper")
    public GetKodeverkKoderBetydningerResponse hentPermisjonstype() {
        return hentKodeverkBetydning(getBuilder("v1/kodeverk/PermisjonsOgPermitteringsBeskrivelse/koder/betydninger", false));
    }

    private Invocation.Builder getBuilder(String path, Boolean eksluderUgyldige) {
        return client.target(endpoint)
                .path(path)
                .queryParam("spraak", SPRAAK)
                .queryParam("ekskluderUgyldige", eksluderUgyldige)
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", ConsumerFactory.CONSUMER_ID);
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

