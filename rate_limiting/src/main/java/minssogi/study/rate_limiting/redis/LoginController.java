package minssogi.study.rate_limiting.redis;

import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.support.BoundedAsyncPool;
import lombok.RequiredArgsConstructor;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

@RestController
@RequestMapping("/redis/reteLimiting")
@RequiredArgsConstructor
public class LoginController {

//    private final RedisTemplate<String, String> redisTemplate;
//    private final RedisCommands<String, String> redisCommands;
    private final Long LOGIN_REQUEST_LIMIT_WHILE_A_SECOND = 500L;
    private final String LOGIN_REQUEST_COUNT_KEY = "loginRequestCount";

//    @PostMapping("/wrong/race_condition/login")
//    public ResponseEntity<String> race_condition_issue(String id, String password) {
//
//        String loginRequestCount = redisCommands.get(LOGIN_REQUEST_COUNT_KEY); // 로그인 요청 횟수가 얼마인지 조회
//
//        if (loginRequestCount == null) {
//            // 조회된 값이 없다면, 1로 초기화하고 ttl을 1초로 설정(1초동안 request를 카운팅하기 위함)
//            redisCommands.set(LOGIN_REQUEST_COUNT_KEY, String.valueOf(1));
//            redisCommands.expire(LOGIN_REQUEST_COUNT_KEY, Duration.ofSeconds(10));
//        } else {
//            // 조회된 값이 있으면 현재 요청 건수까지 더해 기준치까지 도달했는지 확인
//            long totalCountWhileASecond = Long.parseLong(loginRequestCount) + 1L;
//            if (totalCountWhileASecond > LOGIN_REQUEST_LIMIT_WHILE_A_SECOND) { // 기준치를 넘었다면 429 에러를 반환
//                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many requests");
//            } else {  // 기준치를 넘지 않았다면, 계산된 요청 건수를 Redis에 다시 저장
//                redisCommands.set(LOGIN_REQUEST_COUNT_KEY, String.valueOf(totalCountWhileASecond));
//            }
//        }
//
//        if (loginProcess(id, password)) { // 로그인 처리(500ms)
//            return ResponseEntity.ok(id + " Login success");
//        } else {
//            return ResponseEntity.ok(id + " Login failed");
//        }
//    }
//
//    @PostMapping("/correct/race_condition/login")
//    public ResponseEntity<String> race_condition_issue_resolve(String id, String password) {
//        try {
//            Long loginRequestCount = redisCommands.incr(LOGIN_REQUEST_COUNT_KEY); // 로그인 요청 횟수가 얼마인지 조회
//            if (loginRequestCount.equals(1L)) {
//                redisCommands.expire(LOGIN_REQUEST_COUNT_KEY, Duration.ofMinutes(10));
//            }
//
//            if (loginRequestCount > LOGIN_REQUEST_LIMIT_WHILE_A_SECOND) { // 기준치를 넘었다면 429 에러를 반환
//                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many requests");
//            }
//
//            if (loginProcess(id, password)) { // 로그인 처리(500ms)
//                return ResponseEntity.ok(id + " Login success");
//            } else {
//                return ResponseEntity.ok(id + " Login failed");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.ok(id + " Login failed");
//        }
//
//    }
//
//    private final GenericObjectPool<StatefulRedisConnection<String, String>> syncPool;
//    @PostMapping("/sync_pool/login")
//    public ResponseEntity<String> sync_pool(String id, String password) {
//        try (StatefulRedisConnection<String, String> connection = syncPool.borrowObject()) {
//            Long loginRequestCount = connection.sync().incr(LOGIN_REQUEST_COUNT_KEY); // 로그인 요청 횟수가 얼마인지 조회
//            if (loginRequestCount.equals(1L)) {
//                connection.sync().expire(LOGIN_REQUEST_COUNT_KEY, Duration.ofMinutes(10));
//            }
//
//            if (loginRequestCount > LOGIN_REQUEST_LIMIT_WHILE_A_SECOND) { // 기준치를 넘었다면 429 에러를 반환
//                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many requests");
//            }
//
//            if (loginProcess(id, password)) { // 로그인 처리(500ms)
//                return ResponseEntity.ok(id + " Login success");
//            } else {
//                return ResponseEntity.ok(id + " Login failed");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().body("Maybe connection pool error~");
//        }
//    }
//
//    private final CompletionStage<BoundedAsyncPool<StatefulRedisConnection<String, String>>> asyncPool;
//
//    @PostMapping("/async_pool/login")
//    public DeferredResult<ResponseEntity<String>> async_pool(String id, String password) {
//        DeferredResult<ResponseEntity<String>> deferredResult = new DeferredResult<>();
//
//        incrFuture().thenAccept(loginRequestCount -> {
//            if (loginRequestCount > LOGIN_REQUEST_LIMIT_WHILE_A_SECOND) { // 기준치를 넘었다면 429 에러를 반환
//                deferredResult.setResult(ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many requests"));
//                return;
//            }
//
//            if (loginRequestCount == 1L) {
//                expireFuture();
//            }
//
//            if (loginProcess(id, password)) { // 로그인 처리(500ms)
//                deferredResult.setResult(ResponseEntity.ok(id + " Login success"));
//            } else {
//                deferredResult.setResult(ResponseEntity.ok(id + " Login failed"));
//            }
//        });
//
//        return deferredResult;
//    }
//
//    @Async
//    void expireFuture() {
//        asyncPool
//                .thenCompose(BoundedAsyncPool::acquire)
//                .thenApply(connection -> {
//                    try (connection) {
//                        return connection.async();
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .thenCompose(asyncCommands -> asyncCommands.expire(LOGIN_REQUEST_COUNT_KEY, Duration.ofMinutes(10)));
//    }
//
//    private CompletionStage<Long> incrFuture() {
//        return asyncPool.thenCompose(BoundedAsyncPool::acquire).thenApply(connection -> {
//            try (connection) {
//                return connection.async();
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }).thenApply(asyncCommands -> asyncCommands.incr(LOGIN_REQUEST_COUNT_KEY)).thenCompose(RedisFuture::toCompletableFuture);
//    }


    private boolean loginProcess(String id, String password) {
        try {
            Thread.sleep(100); // mock login process
        } catch (Exception ignored) {
            return false;
        }
        return true;
    }
}
