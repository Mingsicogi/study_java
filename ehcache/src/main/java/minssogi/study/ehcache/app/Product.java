package minssogi.study.ehcache.app;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;


@Getter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "UNQ_name", columnNames = {"product_name"})})
@NoArgsConstructor
@ToString
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "product_name")
    private String productName;

    private Integer price;

    private Product(String productName, Integer price) {
        this.productName = productName;
        this.price = price;
    }

    public static Product createProduct(String productName, Integer price) {
        return new Product(productName, price);
    }

    @Transactional
    public void changePrice(Integer price) {
        if (price > 0) {
            this.price = price;
        } else {
            throw new IllegalArgumentException("price must be bigger than 0");
        }
    }
}
