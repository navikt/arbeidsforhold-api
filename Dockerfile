FROM navikt/java:8

ADD ./VERSION /app/VERSION
COPY ./target/arbeidsforhold-api.jar "/app/app.jar"
