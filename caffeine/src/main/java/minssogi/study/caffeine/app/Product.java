package minssogi.study.caffeine.app;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "UNQ_name", columnNames = {"product_name"})})
@NoArgsConstructor
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
}
