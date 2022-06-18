package minssogi.study.resilience4j.rateLimiter;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RateLimiterTestController {

    private final RateLimiterRegistry rateLimiterRegistry;

    private RateLimiter rateLimiter;

    @PostConstruct
    public void init() {
        rateLimiter = rateLimiterRegistry.rateLimiter("rateLimiter");
    }

    @GetMapping("/rateLimiter")
    public ResponseEntity<String> rateLimiter() {
        return rateLimiter.executeTrySupplier(() -> Try.of(() -> ResponseEntity.ok("Rate limiter call success~!"))).recover(RequestNotPermitted.class, throwable -> {
            log.error("### ERROR : Too many request ###", throwable);
            return ResponseEntity.ok("Now too many request... Please try again later few seconds.");
        }).get();
    }
}
