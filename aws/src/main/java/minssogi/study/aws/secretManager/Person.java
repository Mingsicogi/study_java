package minssogi.study.aws.secretManager;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@Entity
@Table(name = "person")
@NoArgsConstructor
@ToString
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Integer age;
    private String uniqueNumber;

    private Person(String name, Integer age) {
        this.name = name;
        this.age = age;
        this.uniqueNumber = UUID.randomUUID().toString();
    }

    public static Person createPerson(String name, Integer age) {
        return new Person(name, age);
    }
}
