package minssogi.study.resilience4j.example;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class Launcher {

    @GetMapping("/launcher/getGameList")
    @Bulkhead(name = "launcherGetGameList", fallbackMethod = "fallbackGetGameList", type = Bulkhead.Type.THREADPOOL)
    public ResponseEntity<String> getGameList() {
        return ResponseEntity.ok(getGameListFromIntegApi());
    }

    private ResponseEntity<String> fallbackGetGameList(Throwable e) {
        return ResponseEntity.ok(String.join(",", Arrays.asList("기본 게임 코드 목록", "UD", "EH", "SL")));
    }

    @SneakyThrows
    private String getGameListFromIntegApi() {
        Thread.sleep(1500);
        return String.join(",", Arrays.asList("integ api 에서 가져온 게임 코드 목록", "UD", "EH", "SL", "UWO", "UDG", "BSL", "DF"));
    }
}
