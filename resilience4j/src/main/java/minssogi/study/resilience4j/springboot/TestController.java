package minssogi.study.resilience4j.springboot;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resilience4j/springboot2")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/circuitBreaker")
    public ResponseEntity<String> circuitBreaker(String param1) {
        return ResponseEntity.ok(testService.circuitBreakerTestMethod(param1));
    }

    @GetMapping("/timeLimiter")
    public ResponseEntity<String> timeLimiter(String param1) {
        return ResponseEntity.ok(testService.timeLimiterTestMethod(param1));
    }

    @GetMapping("/retry")
    public ResponseEntity<String> retry(String param1) {
        return ResponseEntity.ok(testService.retryTestMethod(param1));
    }

    @GetMapping("/bulkhead")
    public ResponseEntity<String> bulkhead(String param1) {
        return ResponseEntity.ok(testService.bulkheadTestMethod(param1));
    }

    @GetMapping("/rateLimiter")
    public ResponseEntity<String> rateLimiter(String param1) {
        return ResponseEntity.ok(testService.rateLimiterTestMethod(param1));
    }

    @GetMapping("/all")
    public ResponseEntity<String> all(String param1) {
        return ResponseEntity.ok(testService.all(param1));
    }
}
