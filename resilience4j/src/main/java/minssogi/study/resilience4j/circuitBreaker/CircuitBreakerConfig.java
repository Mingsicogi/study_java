package minssogi.study.resilience4j.circuitBreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
public class CircuitBreakerConfig {

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        return CircuitBreakerRegistry.of(this.circuitBreakerConfiguration());
    }

    private io.github.resilience4j.circuitbreaker.CircuitBreakerConfig circuitBreakerConfiguration() {
        return io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.custom()
                // 실패 비율 임계치를 백분율로 설정. 실패 비율이 임계치보다 같거나 크면 Circuit이 Open됨.
                .failureRateThreshold(50)

                // 호출이 느린 비율 임계치를 백분율로 설정. slow로 판단하는 기준은 slowCallDurationThreshold 에서 설정한 값.
                .slowCallRateThreshold(50)
                .slowCallDurationThreshold(Duration.of(2, ChronoUnit.SECONDS))

                // circuit breaker가 half open 상태일때 허용할 수 있는 호출 횟수
                .permittedNumberOfCallsInHalfOpenState(10)

                // circuit breaker가 half open 상태를 유지할 수 있는 최대 시간 (0이면 무제한임)
                .maxWaitDurationInHalfOpenState(Duration.ofDays(1))

                // sliding window type이 count-based일때 slidingWindowSize 횟수만큼의 호출을 기록하고 집계. time-based 일땐, slidingWindowSize 초동안 호출을 기록하고 집계.
                .slidingWindowType(io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(10)
                .minimumNumberOfCalls(10) // 집계 기준 최소값

                // open -> half-open 으로 전환하기전 기다리는 시간
                .waitDurationInOpenState(Duration.ofSeconds(10))
                .automaticTransitionFromOpenToHalfOpenEnabled(false) // 별도의 모니터링 스레드를 만들어 전환할지 여부

                // 집계할 예외 대상에 관한 설정.
                .recordExceptions(RuntimeException.class)
                .recordException(e -> {
                    if (e instanceof RuntimeException) {
                        // additional logic when occur RuntimeException

                        return true;
                    }

                    return true;
                })

                // 집계하지 않을 예외 대상에 관한 설정
                .ignoreExceptions(IllegalArgumentException.class, NullPointerException.class)
                .ignoreException(e -> {
                    return false;
                }).build();
    }
}
