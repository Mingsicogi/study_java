package minssogi.study.resilience4j.bulkHead;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BulkheadTestController {

    private final VeryHeavyService veryHeavyService;
    private final BulkheadRegistry bulkheadRegistry;

    private Bulkhead bulkHead;

    @PostConstruct
    public void init() {
        bulkHead = bulkheadRegistry.bulkhead("bulkHead");
    }

    @GetMapping("/bulkHead")
    public ResponseEntity<String> bulkHead() throws Throwable {
        return Bulkhead.decorateCheckedSupplier(bulkHead, veryHeavyService::veryLongTakeTime).recover(throwable -> {
            log.error("### {} ###", throwable.getMessage(), throwable);

            if (throwable instanceof BulkheadFullException) {
                return () -> ResponseEntity.status(503).body("Please try again after few seconds...");
            }

            return () -> ResponseEntity.internalServerError().body("Server Down...");
        }).apply();
    }
}
