package no.nav.arbeidsforhold.api;

import no.nav.arbeidsforhold.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@EnableCaching
@EnableScheduling
public class Launcher {

    public static void main(String... args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("yrker"),
                new ConcurrentMapCache("arbeidsforholdstyper"),
                new ConcurrentMapCache("arbeidstidsordninger"),
                new ConcurrentMapCache("fartsomraader"),
                new ConcurrentMapCache("skipsregistre"),
                new ConcurrentMapCache("skipstyper"),
                new ConcurrentMapCache("land"),
                new ConcurrentMapCache("permisjonstyper"),
                new ConcurrentMapCache("sluttaarsaker")
        ));
        return cacheManager;
    }
}
