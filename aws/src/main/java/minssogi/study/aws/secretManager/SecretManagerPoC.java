package minssogi.study.aws.secretManager;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SecretManagerPoC {

    private final PersonRepository personRepository;

    @PostMapping("/aws/poc/secretManager/person/add")
    public ResponseEntity<Person> add(@RequestBody @Valid PersonDTO.AddReq param) {

        Person savedPerson = personRepository.save(param.toEntity());

        return ResponseEntity.ok(savedPerson);
    }

    @GetMapping("/aws/poc/secretManager/person/findListByName")
    public ResponseEntity<Object> findListByName(@RequestParam String name) {
        List<Person> personList = personRepository.findByName(name);

        if (personList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not exits");
        }

        return ResponseEntity.ok(personList);
    }

}
