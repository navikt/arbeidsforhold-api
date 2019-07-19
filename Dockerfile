FROM navikt/java:8
COPY target/arbeidsforhold-api.jar app.jar
EXPOSE 8080