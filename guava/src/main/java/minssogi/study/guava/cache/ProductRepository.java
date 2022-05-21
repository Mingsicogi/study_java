package minssogi.study.guava.cache;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductByProductName(String productName);

}
