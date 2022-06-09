package minssogi.study.caffeine;

import lombok.SneakyThrows;
import minssogi.study.caffeine.app.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;

@SpringBootTest
class CaffeineApplicationTests {

    @Value("${server.port}")
    private String port;

    private RestTemplate restTemplate = new RestTemplate();

    @SneakyThrows
    @Test
    void contextLoads() {
        String productName = "바닐라크림콜드브루";

        int testSize = 1000;

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        CyclicBarrier cyclicBarrier = new CyclicBarrier(testSize);
        ExecutorService executorService = Executors.newFixedThreadPool(testSize);

        for (int i = 0; i < testSize; i++) {
            executorService.submit(() -> {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }

                ResponseEntity<Product> response = callHttp(productName);
                Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1000, TimeUnit.SECONDS);

        stopWatch.stop();

        System.out.println("=== It tooks " + stopWatch.getTotalTimeMillis() + "ms");
    }

    private ResponseEntity<Product> callHttp(String productName) {
        return restTemplate.getForEntity("http://localhost:" + port + "/product/" + productName, Product.class);
    }
}
