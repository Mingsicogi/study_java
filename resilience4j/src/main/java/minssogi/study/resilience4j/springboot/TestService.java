package minssogi.study.resilience4j.springboot;

import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

@Slf4j
@Service
public class TestService {

    private final String BACKEND = "backendA";

    @CircuitBreaker(name = BACKEND, fallbackMethod = "fallback")
    public String circuitBreakerTestMethod(String param1) {
        log.info("## circuitBreakerTestMethod param1 : {}", param1);
        throw new NumberFormatException();
    }

    @RateLimiter(name = BACKEND)
    public String rateLimiterTestMethod(String param1) {
        log.info("## rateLimiterTestMethod param1 : {}", param1);
        throw new NumberFormatException();
    }

    @Bulkhead(name = BACKEND, fallbackMethod = "fallback")
    public String bulkheadTestMethod(String param1) {
        log.info("## bulkheadTestMethod param1 : {}", param1);
        throw new NumberFormatException();
    }

    @Retry(name = BACKEND)
    public String retryTestMethod(String param1) {
        log.info("## retryTestMethod param1 : {}", param1);
        throw new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE);
    }

    @TimeLimiter(name = BACKEND)
    public String timeLimiterTestMethod(String param1) {
        log.info("## timeLimiterTestMethod param1 : {}", param1);
        throw new NumberFormatException();
    }

    @CircuitBreaker(name = BACKEND, fallbackMethod = "fallback")
    @RateLimiter(name = BACKEND)
    @Bulkhead(name = BACKEND, fallbackMethod = "fallback")
    @Retry(name = BACKEND)
    @TimeLimiter(name = BACKEND)
    public String all(String param1) {
        log.info("## circuitBreakerTestMethod param1 : {}", param1);
        throw new NumberFormatException();
    }

    private String fallback(String param1, CallNotPermittedException e) {
        return "Handled the exception when the CircuitBreaker is open | param1 : " + param1;
    }

    private String fallback(String param1, BulkheadFullException e) {
        return "Handled the exception when the Bulkhead is full | param1 : " + param1;
    }

    private String fallback(String param1, NumberFormatException e) {
        return "Handled the NumberFormatException | param1 : " + param1;
    }

    private String fallback(String param1, Exception e) {
        return "Handled any other exception | param1 : " + param1;
    }
}
