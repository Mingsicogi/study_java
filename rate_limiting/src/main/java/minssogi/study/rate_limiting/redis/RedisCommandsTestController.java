package minssogi.study.rate_limiting.redis;

import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.support.BoundedAsyncPool;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/redis/commands")
public class RedisCommandsTestController {
    private final RedisCommands<String, String> redisCommands;
    @GetMapping("/single/sync")
    public ResponseEntity<Long> singleSync() {
        Long setValue = redisCommands.incr("singleSync");
//        log.info("##### [{} {}] singleSync : {}", Thread.currentThread(), System.currentTimeMillis(), setValue);
        return ResponseEntity.ok(setValue);
    }

    @GetMapping("/single/sync/deferredResult")
    public DeferredResult<ResponseEntity<Long>> singleSyncDeferredResult() {
        DeferredResult<ResponseEntity<Long>> deferredResult = new DeferredResult<>();
        CompletableFuture.runAsync(() -> {
            Long setValue = redisCommands.incr("singleSyncDeferredResult");
            deferredResult.setResult(ResponseEntity.ok(setValue));
        });
        return deferredResult;
    }

    private final RedisTemplate<String, String> redisTemplate;
    @GetMapping("/single/syncTemplate")
    @SneakyThrows
    public ResponseEntity<Long> singleSyncTemplate() {
        Long setValue = redisTemplate.opsForValue().increment("singleSyncTemplate");
//        log.info("##### [{} {}] singleSyncTemplate : {}", Thread.currentThread(), System.currentTimeMillis(), setValue);
//        Thread.sleep(500);
        return ResponseEntity.ok(setValue);
    }

    private final RedisAsyncCommands<String, String> redisAsyncCommands;
    @GetMapping("/single/async")
    @SneakyThrows
    public ResponseEntity<Long> singleAsync() {
//        DeferredResult<Long> deferredResult = new DeferredResult<>();
//        redisAsyncCommands.incr("singleAsync").thenAcceptAsync(setValue -> {
//            deferredResult.setResult(setValue);
////            log.info("##### [{} {}] singleSync : {}", Thread.currentThread(), System.currentTimeMillis(), setValue);
//        });
//        return deferredResult;
        return ResponseEntity.ok(redisAsyncCommands.incr("singleAsync").get(100, TimeUnit.MILLISECONDS));
    }

    private final GenericObjectPool<StatefulRedisConnection<String, String>> redisSyncConnectionPool;
    @GetMapping("/single/syncConnectionPool")
    public ResponseEntity<Long> poolSync() {
        try (StatefulRedisConnection<String, String> connection = redisSyncConnectionPool.borrowObject()) {
            Long setValue = connection.sync().incr("connectionPoolSync");
            return ResponseEntity.ok(setValue);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(-1L);
        }
    }

    private final CompletionStage<BoundedAsyncPool<StatefulRedisConnection<String, String>>> redisAsyncConnectionPool;

    @SneakyThrows
    @GetMapping("/single/asyncConnectionPool")
    public ResponseEntity<Long> poolAsync() {
//        DeferredResult<Long> deferredResult = new DeferredResult<>();
//        redisAsyncConnectionPool
//                .thenComposeAsync(BoundedAsyncPool::acquire)
//                .thenApplyAsync(connection -> {
//                    try (connection) {
//                        return connection.async();
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .thenApplyAsync(asyncCommand -> asyncCommand.incr("connectionPoolAsync"))
//                .thenAcceptAsync(setValue -> setValue.thenAcceptAsync(incrVal -> {
//                            deferredResult.setResult(incrVal);
////                        log.info("##### [{} {}] poolAsync : {}", Thread.currentThread(), System.currentTimeMillis(), incrVal);
//                        }
//                ));
//        return deferredResult;

        return ResponseEntity.ok(redisAsyncConnectionPool
                .thenCompose(BoundedAsyncPool::acquire)
                .thenApply(connection -> {
                    try (connection) {
                        return connection.async();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .thenApply(asyncCommand -> asyncCommand.incr("connectionPoolAsync"))
                .thenCompose(CompletionStage::toCompletableFuture)
                .toCompletableFuture()
                .get(100, TimeUnit.MILLISECONDS));
    }
}


