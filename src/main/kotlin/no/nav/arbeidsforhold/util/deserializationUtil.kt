package no.nav.arbeidsforhold.util

import no.nav.arbeidsforhold.exception.ConsumerException
import javax.ws.rs.ProcessingException
import javax.ws.rs.core.Response

fun <T> readEntity(responsklasse: Class<T>, response: Response): T {
    return try {
        response.readEntity(responsklasse)
    } catch (e: ProcessingException) {
        throw ConsumerException("Prosesseringsfeil på responsobjekt. Responsklasse: " + responsklasse.name, e)
    } catch (e: IllegalStateException) {
        throw ConsumerException("Ulovlig tilstand på responsobjekt. Responsklasse: " + responsklasse.name, e)
    } catch (e: java.lang.Exception) {
        throw ConsumerException("Uventet feil på responsobjektet. Responsklasse: " + responsklasse.name, e)
    }
}

