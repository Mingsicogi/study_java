package minssogi.study.async.sampleHelper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class Case1Helper {

    @SneakyThrows
    @GetMapping("/case1/longTakeTimeTask")
    public ResponseEntity<String> longTakeTimeTask() {
        int takeTime = 5_000;
        Thread.sleep(takeTime);
        log.debug("[longTakeTimeTask] It tooks " + takeTime + "ms");
        return ResponseEntity.ok("[longTakeTimeTask] It tooks " + takeTime + "ms");
    }

    @SneakyThrows
    @GetMapping("/case1/shortTakeTimeTask")
    public ResponseEntity<String> shortTakeTimeTask() {
        int takeTime = 10;
        Thread.sleep(takeTime);
        log.debug("[shortTakeTimeTask] It tooks " + takeTime + "ms");
        return ResponseEntity.ok("[shortTakeTimeTask] It tooks " + takeTime + "ms");
    }


    @SneakyThrows
    @GetMapping("/case1/normalTakeTimeTask")
    public ResponseEntity<String> normalTakeTimeTask() {
        int takeTime = 1500;
        Thread.sleep(takeTime);
        log.debug("[normalTakeTimeTask] It tooks " + takeTime + "ms");
        return ResponseEntity.ok("[normalTakeTimeTask] It tooks " + takeTime + "ms");
    }
}
