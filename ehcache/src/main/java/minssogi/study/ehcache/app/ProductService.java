package minssogi.study.ehcache.app;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    @SneakyThrows
    @Cacheable(value = "productCache", key = "#productName")
    public Product getProduct(String productName)
    {
        Thread.sleep(200);
        return productRepository.findProductByProductName(productName);
    }

    public Product getProductNoCache(String productName) {
        return this.getProduct(productName);
    }
}
