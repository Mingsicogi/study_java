package minssogi.study.ehcache.app;

import minssogi.study.caffeine.app.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductByProductName(String productName);

}
