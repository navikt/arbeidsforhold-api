package no.nav.arbeidsforhold.exceptions;

public class ArbeidsforholdConsumerException extends RuntimeException {

    public ArbeidsforholdConsumerException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ArbeidsforholdConsumerException(String msg) {
        super(msg);
    }
}