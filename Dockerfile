FROM navikt/java:11-appdynamics
COPY target/arbeidsforhold-api.jar app.jar
ENV APPD_ENABLED=true
EXPOSE 8080
