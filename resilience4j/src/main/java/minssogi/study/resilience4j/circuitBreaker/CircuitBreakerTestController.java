package minssogi.study.resilience4j.circuitBreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CircuitBreakerTestController {

    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private CircuitBreaker sampleControllerCircuitBreaker;

    private boolean isError = false;

    @PostConstruct
    public void init() {
        sampleControllerCircuitBreaker = circuitBreakerRegistry.circuitBreaker("sampleControllerCircuitBreaker");
    }

    @GetMapping("/circuitBreaker")
    public ResponseEntity<String> resilience4j() {
        return sampleControllerCircuitBreaker.decorateCheckedSupplier(this::doSomething).recover((throwable -> {
            if (throwable instanceof ClassCastException) {
                return () -> ResponseEntity.ok("Ask to developer...");
            } else if (throwable instanceof RuntimeException) {
                log.warn("### exception : {} ###", throwable.getMessage());
                return () -> ResponseEntity.ok("Please try again after few minutes...");
            } else {
                return () -> ResponseEntity.internalServerError().body("Server is down...");
            }
        })).apply();
    }

    private ResponseEntity<String> doSomething() {
        if (isError) {
            log.error("### ERROR STATUS ###");
            throw new ClassCastException();
        } else {
            log.info("### STATUS OK ###");
            return ResponseEntity.ok("OK");
        }
    }

    @Scheduled(fixedRate = 5000L)
    public void errorFlagSwitch() {
        isError = !isError;
    }
}
