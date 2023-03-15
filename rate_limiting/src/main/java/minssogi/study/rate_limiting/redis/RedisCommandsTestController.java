package minssogi.study.rate_limiting.redis;

import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/redis/commands")
public class RedisCommandsTestController {

    private final RedisCommands<String, String> redisCommands;
//    private final RedisAsyncCommands<String, String> redisAsyncCommands;

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/single/sync")
    public ResponseEntity<Long> singleSync() {
        Long setValue = redisCommands.incr("singleSync");
        log.info("##### [{} {}] singleSync : {}", Thread.currentThread(), System.nanoTime(), setValue);


//        log.info("##### [{} {}] singleSync : {}", Thread.currentThread(), System.nanoTime(), counter.incrementAndGet());
        return ResponseEntity.ok(setValue);
    }

//    @GetMapping("/single/async")
//    public DeferredResult<Long> singleAsync() {
//        DeferredResult<Long> deferredResult = new DeferredResult<>();
//        redisAsyncCommands.incr("singleAsync").thenAccept(deferredResult::setResult);
//        return deferredResult;
//    }
}
