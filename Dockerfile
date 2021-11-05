FROM navikt/java:11-appdynamics
COPY target/arbeidsforhold-api.jar app.jar
ENV APPD_ENABLED=true
ENV APP_LOG_HOME="/tmp"
ENV contextName="arbeidsforhold-api"
EXPOSE 8080
