package minssogi.study.aws.secretManager;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class PersonDTO {

    @Getter
    @NoArgsConstructor
    public static class AddReq {
        @NotBlank(message = "name cannot be null")
        private String name;
        @Min(value = 1)
        @Max(value = 200)
        private int age;

        public Person toEntity() {
            return Person.createPerson(name, age);
        }
    }

    @Getter
    public static class FindListByName {
        @NotBlank(message = "name cannot be null")
        private String name;
    }
}
