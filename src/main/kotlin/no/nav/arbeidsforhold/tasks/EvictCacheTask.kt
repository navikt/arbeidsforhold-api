package no.nav.arbeidsforhold.tasks

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.CacheManager
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class EvictCacheTask {

    private val log = LoggerFactory.getLogger(EvictCacheTask::class.java)

    @Autowired
    private val cacheManager: CacheManager? = null

    @Scheduled(fixedRate = (6 * 60 * 60 * 1000).toLong())
    fun evictCache() {
        log.info("Evicting caches.")
        cacheManager!!.cacheNames.stream().forEach { cacheName ->
            cacheManager.getCache(cacheName)!!.clear()
        }
    }
}