package no.nav.arbeidsforhold.exceptions;

public class EregConsumerException extends RuntimeException {

    public EregConsumerException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public EregConsumerException(String msg) {
        super(msg);
    }
}