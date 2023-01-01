package minssogi.study.ehcache.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import minssogi.study.ehcache.cd._CacheDefine;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import java.util.concurrent.TimeUnit;

//@EnableCaching
//@Configuration
public class _CaffeineCacheConfiguration {

//    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        for (_CacheDefine define : _CacheDefine.values()) {
            cacheManager.registerCustomCache(define.getCacheName(), Caffeine.newBuilder().refreshAfterWrite(define.getTtl(), define.getTtlUnit()).build(new CacheLoader<Object, Object>() {
                @Override
                public @Nullable Object load(Object key) throws Exception {
                    return define.getDataLoadFunction().apply(key);
                }
            }));
        }

        return cacheManager;
    }

    private Cache<Object, Object> productCache() {
        return Caffeine.newBuilder().expireAfterAccess(1, TimeUnit.SECONDS).build();
    }
}
