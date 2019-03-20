package no.nav.arbeidsforhold.config;

import no.nav.log.LogFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootConfiguration
@ComponentScan({"no.nav.arbeidsforhold.features", "no.nav.arbeidsforhold.api"})
@Import({RestClientConfiguration.class,
})
public class ApplicationTestConfig implements EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(ApplicationTestConfig.class);

    private Environment env;

    @Bean
    ServletWebServerFactory servletWebServerFactory() {
        JettyServletWebServerFactory serverFactory = new JettyServletWebServerFactory();
        serverFactory.setPort(8080);
        return serverFactory;
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }


    @Bean
    public ResourceConfig jerseyConfig() {
        return new RestResourceConfiguration();
    }

    @Bean
    public FilterRegistrationBean<LogFilter> logFilter() {
        log.info("Registering LogFilter filter");
        final FilterRegistrationBean<LogFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new LogFilter());
        filterRegistration.setOrder(1);
        return filterRegistration;
    }

    @Override
    public void setEnvironment(Environment env) {
        this.env = env;
    }

}
