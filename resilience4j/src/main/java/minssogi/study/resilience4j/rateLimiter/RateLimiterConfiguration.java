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
                .timeoutDuration(Duration.ofMillis(10)) // 초과된 요청을 얼만큼 기다릴지 시간 설정.
                .limitRefreshPeriod(Duration.ofSeconds(3)) // limit for period 설정이 초기화되는 주기.
                .limitForPeriod(10) // limit refresh period 로 refresh 되기전에 허용되는 request 수
                .writableStackTraceEnabled(true)
                .build();
    }
}
