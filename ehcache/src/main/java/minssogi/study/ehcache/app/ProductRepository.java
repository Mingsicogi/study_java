package minssogi.study.ehcache.app;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductByProductName(String productName);

}
