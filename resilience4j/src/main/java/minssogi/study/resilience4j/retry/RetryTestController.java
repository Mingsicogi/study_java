package minssogi.study.resilience4j.retry;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RetryTestController {

    private final RetryRegistry retryRegistry;

    private Retry retry;

    @PostConstruct
    public void init() {
        retry = retryRegistry.retry("retry");
    }

    @GetMapping("/retry")
    public ResponseEntity<String> retry() {
        return retry.executeTrySupplier(() -> Try.of(() -> {
            log.info("### retry service start ###");
            if (true) {
                throw new IOException();
            }

            return ResponseEntity.ok("Retry call success");
        })).recover(throwable -> ResponseEntity.ok("Retry service not working now. Ask to developer~")).get();
    }
}
