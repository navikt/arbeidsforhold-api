package no.nav.arbeidsforhold.exception

class KodeverkConsumerException : RuntimeException {
    constructor(msg: String?, cause: Throwable?) : super(msg, cause)
    constructor(msg: String?) : super(msg)
}