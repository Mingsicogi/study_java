package minssogi.study.resilience4j_demo.example;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@Slf4j
@RestController
public class Launcher {

    @GetMapping("/launcher/getGameList")
    @Bulkhead(name = "launcherGetGameList", fallbackMethod = "fallbackGetGameList", type = Bulkhead.Type.SEMAPHORE)
    public ResponseEntity<String> getGameList() {
        return ResponseEntity.ok(getGameListFromIntegApi());
    }

    private ResponseEntity<String> fallbackGetGameList(Throwable e) {
        log.info("error message : {}", e.getMessage());
        return ResponseEntity.ok(String.join(",", Arrays.asList("기본 게임 코드 목록", "UD", "EH", "SL")));
    }

    @SneakyThrows
    private String getGameListFromIntegApi() {
        Thread.sleep(500);
        return String.join(",", Arrays.asList("integ api 에서 가져온 게임 코드 목록", "UD", "EH", "SL", "UWO", "UDG", "BSL", "DF"));
    }
}
