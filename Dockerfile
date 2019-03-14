FROM navikt/java:8

ADD ./VERSION /app/VERSION
COPY ./target/arbeidsforhold.jar "/app/app.jar"
