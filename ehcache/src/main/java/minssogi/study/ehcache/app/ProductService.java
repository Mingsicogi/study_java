package minssogi.study.ehcache.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Cacheable(value = "productCache", key = "#productName")
    public Product getProduct(String productName) {
        return productRepository.findProductByProductName(productName);
    }
}
