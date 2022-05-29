package minssogi.study.resilience4j.rateLimiter;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimiterConfiguration {

    @Bean
    public RateLimiterRegistry rateLimiterRegistry() {
        return RateLimiterRegistry.of(this.rateLimiterConfig());
    }

    private RateLimiterConfig rateLimiterConfig() {
        return RateLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(10))
                .limitRefreshPeriod(Duration.ofSeconds(3))
                .limitForPeriod(10)
                .writableStackTraceEnabled(true)
                .build();
    }
}
