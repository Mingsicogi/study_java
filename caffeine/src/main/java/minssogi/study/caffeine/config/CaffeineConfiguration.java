package minssogi.study.caffeine.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.RequiredArgsConstructor;
import minssogi.study.caffeine.app.Product;
import minssogi.study.caffeine.app.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class CaffeineConfiguration {

    private final ProductRepository productRepository;

    @Bean
    public LoadingCache<String, Product> productCache() {
        return Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(Duration.ofMinutes(5))
                .refreshAfterWrite(Duration.ofMinutes(1))
                .build(productRepository::findProductByProductName);
    }
}
