package minssogi.study.caffeine.app;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
