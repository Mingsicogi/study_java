package minssogi.study.ehcache.app;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Cacheable(value = "productCache", key = "#productName")
    Product findProductByProductName(String productName);

}
