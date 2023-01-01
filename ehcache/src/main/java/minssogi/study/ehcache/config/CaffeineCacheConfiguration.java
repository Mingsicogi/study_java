package minssogi.study.ehcache.config;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import minssogi.study.ehcache.cd.CacheTypeCd;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CaffeineCacheConfiguration {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        for (CacheTypeCd cacheCd : CacheTypeCd.values()) {
            cacheManager.registerCustomCache(cacheCd.getCacheName(), Caffeine.newBuilder().refreshAfterWrite(cacheCd.getTtl(), cacheCd.getTtlUnit()).build(new CacheLoader<Object, Object>() {
                @Override
                public @Nullable Object load(Object key) throws Exception {
                    return cacheCd.getDataLoadFunction().apply(key);
                }
            }));
        }

        return cacheManager;
    }
}
