package minssogi.study.resilience4j.rateLimiter;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class RateLimiterTestControllerTest {

    @Autowired
    MockMvc mockMvc;


    @Test
    @SneakyThrows
    void rateLimiter() {
        int testThreadCount = 100;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(testThreadCount-1);
        ExecutorService executorService = Executors.newFixedThreadPool(testThreadCount);

        for (int i = 0; i < testThreadCount; i++) {
            executorService.submit(() -> mockMvc.perform(get("/rateLimiter")));
//            cyclicBarrier.await();
        }

        executorService.awaitTermination(3, TimeUnit.SECONDS);
    }
}