package minssogi.study.resilience4j.bulkHead;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VeryHeavyService {

    @SneakyThrows
    public ResponseEntity<String> veryLongTakeTime() {
        log.info("### veryLongTakeTime method start ###");
        Thread.sleep(1000);
        String successMessage = "veryLongTakeTime method finish successfully~!";
        log.info("### {} ###", successMessage);
        return ResponseEntity.ok(successMessage);
    }
}
