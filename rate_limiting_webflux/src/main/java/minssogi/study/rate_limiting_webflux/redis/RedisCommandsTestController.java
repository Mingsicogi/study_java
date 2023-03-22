package minssogi.study.rate_limiting_webflux.redis;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.support.BoundedAsyncPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletionStage;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/redis/commands")
public class RedisCommandsTestController {
//    private final RedisCommands<String, String> redisCommands;
//    @GetMapping("/single/sync")
//    public ResponseEntity<Long> singleSync() {
//        Long setValue = redisCommands.incr("singleSync");
//        log.info("##### [{} {}] singleSync : {}", Thread.currentThread(), System.currentTimeMillis(), setValue);
//        return ResponseEntity.ok(setValue);
//    }

//    private final RedisAsyncCommands<String, String> redisAsyncCommands;
//    @GetMapping("/single/async")
//    public DeferredResult<Long> singleAsync() {
//        DeferredResult<Long> deferredResult = new DeferredResult<>();
//        redisAsyncCommands.incr("singleAsync").thenAcceptAsync(setValue -> {
//            deferredResult.setResult(setValue);
//            log.info("##### [{} {}] singleSync : {}", Thread.currentThread(), System.currentTimeMillis(), setValue);
//        });
//        return deferredResult;
//    }

//    private final GenericObjectPool<StatefulRedisConnection<String, String>> redisSyncConnectionPool;
//    @GetMapping("/single/syncConnectionPool")
//    public ResponseEntity<Long> poolSync() {
//        try (StatefulRedisConnection<String, String> connection = redisSyncConnectionPool.borrowObject()) {
//            Long setValue = connection.sync().incr("connectionPoolSync");
//            log.info("##### [{} {}] poolSync : {}", Thread.currentThread(), System.currentTimeMillis(), setValue);
//            return ResponseEntity.ok(setValue);
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().body(-1L);
//        }
//    }

    private final CompletionStage<BoundedAsyncPool<StatefulRedisConnection<String, String>>> redisAsyncConnectionPool;

//    @GetMapping("/single/asyncConnectionPool/webflux")
//    public Mono<Long> poolAsync() {
//        return Mono.fromCompletionStage(redisAsyncConnectionPool
//                .thenCompose(BoundedAsyncPool::acquire)
//                .thenApply(connection -> {
//                    try (connection) {
//                        return connection.async();
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .thenApply(asyncCommand -> asyncCommand.incr("connectionPoolAsync"))
//                .thenCompose(CompletionStage::toCompletableFuture)
//        );
//    }

//    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
    @GetMapping("/single/webflux")
    public Mono<Long> webflux() {
        return Mono.fromCompletionStage(redisAsyncConnectionPool
            .thenCompose(BoundedAsyncPool::acquire)
            .thenApply(connection -> {
                try (connection) {
                    return connection.reactive();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            })
            .thenApply(reactiveCommand -> reactiveCommand.incr("webflux"))
        ).flatMap(a -> a);

//        return reactiveRedisTemplate.opsForValue().increment("webflux");
    }
}


