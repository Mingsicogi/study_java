package minssogi.study.rate_limiting.redis;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.TimeoutOptions;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RedisConfiguration {

    private final String REDIS_HOST = "localhost";
    private final int REDIS_PORT = 6379;

    private final RedisClient redisClient;

    public RedisConfiguration() {
        this.redisClient = RedisClient.create("redis://" + REDIS_HOST + ":" + REDIS_PORT);
        this.redisClient.setOptions(ClientOptions.builder().timeoutOptions(TimeoutOptions.builder().fixedTimeout(Duration.ofMillis(100)).build()).build());
    }

    @Bean
    public RedisCommands<String, String> redisCommands() {
        return redisClient.connect().sync();
    }
}
