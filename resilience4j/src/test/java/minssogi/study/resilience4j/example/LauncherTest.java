package minssogi.study.resilience4j.example;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
class LauncherTest {

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    @SneakyThrows
    void getGameList() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                try {
                    ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8083/launcher/getGameList", String.class);
                    log.info("[{}] res = {}", Thread.currentThread().getName(), response.getBody());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.awaitTermination(2, TimeUnit.SECONDS);
    }
}