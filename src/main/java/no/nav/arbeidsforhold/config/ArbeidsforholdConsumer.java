package no.nav.arbeidsforhold.config;

import no.nav.arbeidsforhold.domain.Arbeidsforhold;
import no.nav.arbeidsforhold.exceptions.ArbeidsforholdConsumerException;
import no.nav.arbeidsforhold.services.tokendings.TokenDingsService;
import no.nav.log.MDCConstants;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;
import static no.nav.arbeidsforhold.util.TokenUtilKt.getToken;

public class ArbeidsforholdConsumer {

    private static final String CONSUMER_ID = "personbruker-arbeidsforhold-api";
    private static final String BEARER = "Bearer ";
    private static final String REGELVERK = "A_ORDNINGEN";
    private static final String ARBEIDSFORHOLDTYPER="ordinaertArbeidsforhold,maritimtArbeidsforhold,forenkletOppgjoersordning,frilanserOppdragstakerHonorarPersonerMm";
    private Client client;
    private URI endpoint;
    private TokenDingsService tokenDingsService;

    @Value("${PERSONOPPLYSNINGER_PROXY_TARGET_APP}")
    private String targetApp;

    public ArbeidsforholdConsumer(Client client, URI endpoint, TokenDingsService tokenDingsService) {
        this.client = client;
        this.endpoint = endpoint;
        this.tokenDingsService = tokenDingsService;
    }

    public List<Arbeidsforhold> hentArbeidsforholdmedFnr(String fnr) {
        String accessToken = tokenDingsService.exchangeToken(getToken(), targetApp).getAccessToken();
        Invocation.Builder request = buildFnrRequest(fnr, accessToken);
        return hentArbeidsforholdmedFnr(request);
    }

    public Arbeidsforhold hentArbeidsforholdmedId(String fnr, int id) {
        String accessToken = tokenDingsService.exchangeToken(getToken(), targetApp).getAccessToken();
        Invocation.Builder request = buildForholdIdRequest(fnr, id, accessToken);
        return hentArbeidsforholdmedId(request);
    }

    private Invocation.Builder buildFnrRequest(String fnr, String accessToken) {

        return client.target(endpoint)
                .path("v1/arbeidstaker/arbeidsforhold")
                .queryParam("regelverk", REGELVERK)
                .queryParam("sporingsinformasjon", false)
                .queryParam("arbeidsforholdtype",ARBEIDSFORHOLDTYPER)
                .request()
                .header(HttpHeaders.AUTHORIZATION, BEARER.concat(accessToken))
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", CONSUMER_ID)
                .header("Nav-Consumer-Token", getToken())
                .header("Nav-Personident", fnr);

    }

    private Invocation.Builder buildForholdIdRequest(String fnr, int id, String accessToken) {
        return client.target(endpoint)
                .path("v1/arbeidsforhold/" + id)
                .queryParam("historikk", true)
                .queryParam("sporingsinformasjon", false)
                .request()
                .header(HttpHeaders.AUTHORIZATION, BEARER.concat(accessToken))
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", CONSUMER_ID)
                .header("Nav-Consumer-Token", getToken())
                .header("Nav-Personident", fnr);
    }

    private List<Arbeidsforhold> hentArbeidsforholdmedFnr(Invocation.Builder request) {
        try (Response response = request.get()) {
            return readFnrResponse(response);
        } catch (ArbeidsforholdConsumerException e) {
            throw e;
        } catch (Exception e) {
            String msg = "Forsøkte å konsumere REST-tjenesten Arbeidsforhold. endpoint=[" + endpoint + "].";
            throw new ArbeidsforholdConsumerException(msg, e);
        }
    }

    private Arbeidsforhold hentArbeidsforholdmedId(Invocation.Builder request) {
        try (Response response = request.get()) {
            return readIdResponse(response);
        } catch (ArbeidsforholdConsumerException e) {
            throw e;
        } catch (Exception e) {
            String msg = "Forsøkte å konsumere REST-tjenesten Arbeidsforhold. endpoint=[" + endpoint + "].";
            throw new ArbeidsforholdConsumerException(msg, e);
        }
    }

    private List<Arbeidsforhold> readFnrResponse(Response r) {
        if (!SUCCESSFUL.equals(r.getStatusInfo().getFamily())) {
            String msg = "Forsøkte å konsumere REST-tjenesten Arbeidsforhold. endpoint=[" + endpoint + "], HTTP response status=[" + r.getStatus() + "].";
            throw new ArbeidsforholdConsumerException(msg + " - " + readEntity(String.class, r));
        } else {
            List arbeidsforholdList = r.readEntity(new GenericType<List<Arbeidsforhold>>() {
            });
            return arbeidsforholdList;
        }
    }

    private Arbeidsforhold readIdResponse(Response r) {
        if (!SUCCESSFUL.equals(r.getStatusInfo().getFamily())) {
            String msg = "Forsøkte å konsumere REST-tjenesten Arbeidsforhold. endpoint=[" + endpoint + "], HTTP response status=[" + r.getStatus() + "].";
            throw new ArbeidsforholdConsumerException(msg + " - " + readEntity(String.class, r));
        } else {
            Arbeidsforhold arbeidsforhold = r.readEntity(Arbeidsforhold.class);
            return arbeidsforhold;
        }
    }

    private <T> T readEntity(Class<T> responsklasse, Response response) {
        try {
            return response.readEntity(responsklasse);
        } catch (ProcessingException e) {
            throw new ArbeidsforholdConsumerException("Prosesseringsfeil på responsobjekt. Responsklasse: " + responsklasse.getName(), e);
        } catch (IllegalStateException e) {
            throw new ArbeidsforholdConsumerException("Ulovlig tilstand på responsobjekt. Responsklasse: " + responsklasse.getName(), e);
        } catch (Exception e) {
            throw new ArbeidsforholdConsumerException("Uventet feil på responsobjektet. Responsklasse: " + responsklasse.getName(), e);
        }
    }


}
