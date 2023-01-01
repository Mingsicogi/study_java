package minssogi.study.resilience4j.bulkHead;

import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.time.Duration;

@EnableAsync
@Configuration
public class BulkHeadConfiguration {

    @Bean
    public BulkheadRegistry bulkheadRegistry() {
        return BulkheadRegistry.of(this.bulkheadConfig());
    }

    private BulkheadConfig bulkheadConfig() {
        return BulkheadConfig.custom()
                .maxConcurrentCalls(5) // 동시에 실행 가능한 thread 수
                .maxWaitDuration(Duration.ofMillis(500)) // 동시에 실행 가능한 max thread에 도달했을때 진입하려는 thread 의 max wait time
                .writableStackTraceEnabled(false) // Exception 발생시 로그를 출력할지 여부
                .fairCallHandlingStrategyEnabled(true) // true일 경우, 요청 순서를 보장해서 처리함.
                .build();
    }
}
