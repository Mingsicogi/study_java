package minssogi.study.caffeine.app;

import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final LoadingCache<Object, Object> productCache;
    private final ProductRepository productRepository;

    public Product getProductWithCache(String productName) {
        return (Product) productCache.get(productName);
    }

    @Cacheable(value = "productCache", key = "#productName")
    public Product getProduct(String productName) {
        return productRepository.findProductByProductName(productName);
    }
}
