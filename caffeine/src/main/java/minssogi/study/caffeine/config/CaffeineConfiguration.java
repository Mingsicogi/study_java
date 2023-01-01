package minssogi.study.caffeine.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.RequiredArgsConstructor;
import minssogi.study.caffeine.app.ProductRepository;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class CaffeineConfiguration {

    private final ProductRepository productRepository;

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setAllowNullValues(false);
        cacheManager.registerCustomCache("productCache", productCache());
        return cacheManager;
    }

    @Bean
    public LoadingCache<Object, Object> productCache() {
        return Caffeine.newBuilder()
                .maximumSize(10_000)
//                .expireAfterWrite(Duration.ofMinutes(5))
                .refreshAfterWrite(Duration.ofSeconds(1))
                .build((key) -> productRepository.findProductByProductName((String) key));
    }
}
