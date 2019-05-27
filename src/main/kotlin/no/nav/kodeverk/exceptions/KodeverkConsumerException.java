package no.nav.kodeverk.exceptions;
public class KodeverkConsumerException extends RuntimeException {

    public KodeverkConsumerException(String msg, Throwable cause) {
        super(msg, cause);
    }


    public KodeverkConsumerException(String msg) {
        super(msg);
    }
}
