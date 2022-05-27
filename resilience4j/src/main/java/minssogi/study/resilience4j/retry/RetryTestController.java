package minssogi.study.resilience4j.retry;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

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


}
