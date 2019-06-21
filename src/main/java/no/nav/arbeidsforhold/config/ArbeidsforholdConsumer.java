package no.nav.arbeidsforhold.config;

import no.nav.arbeidsforhold.domain.Arbeidsforhold;
import no.nav.arbeidsforhold.exceptions.ArbeidsforholdConsumerException;
import no.nav.log.MDCConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;


import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;

public class ArbeidsforholdConsumer {

    private static final String CONSUMER_ID = "personbruker-arbeidsforhold-api";
    private static final String BEARER = "Bearer ";
    private static final String REGELVERK = "A_ORDNINGEN";
    private Client client;
    private URI endpoint;
    private static final Logger log = LoggerFactory.getLogger(ArbeidsforholdConsumer.class);


    public ArbeidsforholdConsumer(Client client, URI endpoint) {
        this.client = client;
        this.endpoint = endpoint;
    }


    public List<Arbeidsforhold> hentArbeidsforholdmedFnr(String fnr, String fssToken) {
        Invocation.Builder request = buildFnrRequest(fnr, fssToken);
        return hentArbeidsforholdmedFnr(request);
    }


    public Arbeidsforhold hentArbeidsforholdmedId(String fnr, int id, String fssToken) {
        Invocation.Builder request = buildForholdIdRequest(fnr, id, fssToken);
        return hentArbeidsforholdmedId(request);
    }


    private Invocation.Builder buildFnrRequest(String fnr, String fssToken) {

        return client.target(endpoint)
                .path("v1/arbeidstaker/arbeidsforhold")
                .queryParam("regelverk", REGELVERK)
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", CONSUMER_ID)
                .header("Nav-Consumer-Token", BEARER.concat(fssToken))
                .header("Nav-Personident", fnr);

    }

    private Invocation.Builder buildForholdIdRequest(String fnr, int id, String fssToken) {
        return client.target(endpoint)
                .path("v1/arbeidsforhold/" + id)
                .queryParam("historikk", true)
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", CONSUMER_ID)
                .header("Nav-Consumer-Token", BEARER.concat(fssToken))
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
            //log.warn("Arbeidsforhold: " + r.readEntity(String.class));
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
            //log.warn("Arbeidsforholdinnslag: " + r.readEntity(String.class));
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