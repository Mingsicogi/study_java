package minssogi.study.guava.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Test
    void getProductWithCache() {
        // GIVE
        String productName = "바닐라크림콜드브루";
        Integer price = 7200;
        Product savedData = productRepository.saveAndFlush(Product.createProduct(productName, price));

        // WHEN
        Product selectedData = productService.getProductWithCache(productName);
        selectedData = productService.getProductWithCache(productName);

        // THEN
        Assertions.assertEquals(savedData.getProductName(), selectedData.getProductName());
    }
}