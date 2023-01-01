package minssogi.study.resilience4j.example;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class STSTest {
    private final RestTemplate restTemplate = new RestTemplate();

    private final Map<String, Integer> statistics = new ConcurrentHashMap<>();

    @Test
    @SneakyThrows
    void stsLoginReq() {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                try {
                    ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8083/sts/login/req", String.class);
                    statistics.merge(response.getBody(), 1, Integer::sum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            if ((i+1) % 10 == 0) {
                Thread.sleep(500);
                log.info("#### {} threads finish ###", i+1);
            }
        }

        executorService.awaitTermination(2, TimeUnit.SECONDS);

        log.info("statistics = {}", statistics);
    }
}