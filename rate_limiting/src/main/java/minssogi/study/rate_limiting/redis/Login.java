package minssogi.study.rate_limiting.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis/reteLimiting")
@RequiredArgsConstructor
public class Login {

    private final RedisTemplate<String, String> redisTemplate;
    private final Long LOGIN_REQUEST_LIMIT_WHILE_A_SECOND = 100L;
    private final String LOGIN_REQUEST_COUNT_KEY = "loginRequestCount";

    @PostMapping("/bad_case/race_condition/login")
    public ResponseEntity<String> login(String id, String password) {
        String loginRequestCount = redisTemplate.opsForValue().get(LOGIN_REQUEST_COUNT_KEY);
        if (loginRequestCount == null) {
            redisTemplate.opsForValue().set(LOGIN_REQUEST_COUNT_KEY, String.valueOf(1));
            redisTemplate.expire(LOGIN_REQUEST_COUNT_KEY, 1L, java.util.concurrent.TimeUnit.SECONDS);
        } else {
            long totalCountWhileASecond = Long.parseLong(loginRequestCount) + 1L;
            if (totalCountWhileASecond > LOGIN_REQUEST_LIMIT_WHILE_A_SECOND) {
                return ResponseEntity.ok("Too many requests");
            } else {
                redisTemplate.opsForValue().set(LOGIN_REQUEST_COUNT_KEY, String.valueOf(totalCountWhileASecond));
            }
        }

        if (loginProcess(id, password)) {
            return ResponseEntity.ok(id + " Login success");
        } else {
            return ResponseEntity.ok(id + " Login failed");
        }
    }


    private boolean loginProcess(String id, String password) {
        try {
            Thread.sleep(500); // mock login process
        } catch (Exception ignored) {
            return false;
        }

        return true;
    }
}
