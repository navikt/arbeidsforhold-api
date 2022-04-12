package no.nav.arbeidsforhold.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EvictCacheTask {

    private static final Logger log = LoggerFactory.getLogger(EvictCacheTask.class);

    @Autowired
    private CacheManager cacheManager;

    @Scheduled(fixedRate = 6 * 60 * 60 * 1000)
    public void evictCache() {
        log.info("Evicting caches.");
        cacheManager.getCacheNames().forEach(
                cacheName -> Objects.requireNonNull(cacheManager.getCache(cacheName)).clear());
    }

}
