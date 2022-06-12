package minssogi.study.ehcache;

import lombok.SneakyThrows;
import minssogi.study.ehcache.app.Product;
import minssogi.study.ehcache.app.ProductRepository;
import minssogi.study.ehcache.app.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.concurrent.*;

@SpringBootTest
class EhcacheApplicationTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @SneakyThrows
    @Test
    void contextLoads() {
        String productName = "바닐라크림콜드브루";
        Integer price = 7200;
        Product savedData = productRepository.saveAndFlush(Product.createProduct(productName, price));
        productRepository.flush();
        System.out.println("==== Test data setting completion ====");

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

                System.out.println(productService.getProduct(productName).toString());
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1000, TimeUnit.SECONDS);
    }

}
