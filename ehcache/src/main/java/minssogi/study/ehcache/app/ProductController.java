package minssogi.study.ehcache.app;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;

    @PostConstruct
    public void init() {
        String productName = "바닐라크림콜드브루";
        Integer price = 7200;
        productRepository.saveAndFlush(Product.createProduct(productName, price));
    }

    @GetMapping("/{productName}")
    public ResponseEntity<Product> findProduct(@PathVariable("productName") String productName) {
        return ResponseEntity.ok(productService.getProduct(productName));
    }

    @GetMapping("/{productName}/{changePrice}")
    @Transactional
    public ResponseEntity<Product> modifyProductName(@PathVariable("productName") String productName, @PathVariable("changePrice") Integer changePrice) {
        Product productByProductName = productRepository.findProductByProductName(productName);
        productByProductName.changePrice(changePrice);
        return ResponseEntity.ok(productByProductName);
    }
}
