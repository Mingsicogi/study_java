package minssogi.study.rate_limiting.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redis/reteLimiting")
@RequiredArgsConstructor
public class LoginController {

    private final RedisTemplate<String, String> redisTemplate;

    private final Long LOGIN_REQUEST_LIMIT_WHILE_A_SECOND = 2L;
    private final String LOGIN_REQUEST_COUNT_KEY = "loginRequestCount";

    @PostMapping("/wrong/race_condition/login")
    public ResponseEntity<String> wrong(String id, String password) {

        String loginRequestCount = redisTemplate.opsForValue().get(LOGIN_REQUEST_COUNT_KEY); // 로그인 요청 횟수가 얼마인지 조회

        if (loginRequestCount == null) {
            // 조회된 값이 없다면, 1로 초기화하고 ttl을 1초로 설정(1초동안 request를 카운팅하기 위함)
            redisTemplate.opsForValue().set(LOGIN_REQUEST_COUNT_KEY, String.valueOf(1));
            redisTemplate.expire(LOGIN_REQUEST_COUNT_KEY, 10L, java.util.concurrent.TimeUnit.SECONDS);
        } else {
            // 조회된 값이 있으면 현재 요청 건수까지 더해 기준치까지 도달했는지 확인
            long totalCountWhileASecond = Long.parseLong(loginRequestCount) + 1L;
            if (totalCountWhileASecond > LOGIN_REQUEST_LIMIT_WHILE_A_SECOND) { // 기준치를 넘었다면 429 에러를 반환
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many requests");
            } else {  // 기준치를 넘지 않았다면, 계산된 요청 건수를 Redis에 다시 저장
                redisTemplate.opsForValue().set(LOGIN_REQUEST_COUNT_KEY, String.valueOf(totalCountWhileASecond));
            }
        }

        if (loginProcess(id, password)) { // 로그인 처리(500ms)
            return ResponseEntity.ok(id + " Login success");
        } else {
            return ResponseEntity.ok(id + " Login failed");
        }
    }

    @PostMapping("/correct/race_condition/login")
    public ResponseEntity<String> correct(String id, String password) {

        Long loginRequestCount = redisTemplate.opsForValue().increment(LOGIN_REQUEST_COUNT_KEY); // 로그인 요청 횟수가 얼마인지 조회
        if (loginRequestCount.equals(1L)) {
            redisTemplate.expire(LOGIN_REQUEST_COUNT_KEY, 1L, TimeUnit.SECONDS);
        }

        if (loginRequestCount > LOGIN_REQUEST_LIMIT_WHILE_A_SECOND) { // 기준치를 넘었다면 429 에러를 반환
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many requests");
        }

        if (loginProcess(id, password)) { // 로그인 처리(500ms)
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
