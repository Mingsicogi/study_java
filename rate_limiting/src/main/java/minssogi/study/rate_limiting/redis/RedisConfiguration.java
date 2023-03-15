package minssogi.study.rate_limiting.redis;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.TimeoutOptions;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.StringCodec;
import io.lettuce.core.protocol.AsyncCommand;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultEventLoopGroupProvider;
import io.lettuce.core.support.AsyncConnectionPoolSupport;
import io.lettuce.core.support.BoundedAsyncPool;
import io.lettuce.core.support.BoundedPoolConfig;
import io.lettuce.core.support.ConnectionPoolSupport;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.scheduling.annotation.EnableAsync;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

@EnableAsync
@Configuration
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class RedisConfiguration {

//    private final String REDIS_HOST = "localhost";
    private final String REDIS_HOST = "localhost"; // network latency를 늘리기 위해 AWS EC2에 Redis를 설치
    private final int REDIS_PORT = 6379;
    private final String REDIS_CON_URL = "redis://" + REDIS_HOST + ":" + REDIS_PORT;

    @Bean
    public RedisCommands<String, String> redisCommands() {
        ClientResources nettyClient = ClientResources.builder()
                .ioThreadPoolSize(1)
                .computationThreadPoolSize(1)
                .eventExecutorGroup(new NioEventLoopGroup(1, new DefaultThreadFactory("minssogi-executor-thread")))
//                .eventLoopGroupProvider(new DefaultEventLoopGroupProvider(1))
                .build();

        RedisClient redisClient = RedisClient.create(nettyClient, REDIS_CON_URL);

//        RedisClient redisClient = RedisClient.create(REDIS_CON_URL);
        redisClient.setOptions(ClientOptions.builder().timeoutOptions(TimeoutOptions.builder().fixedTimeout(Duration.ofMillis(100)).build()).build());

        return redisClient.connect().sync();
    }

//    @Bean
//    public RedisAsyncCommands<String, String> asyncRedisCommands() {
//        return redisClient.connect().async();
//    }
//
//    @Bean
//    public GenericObjectPool<StatefulRedisConnection<String, String>> syncPool() {
//        int redisClientCount = 10;
//        GenericObjectPoolConfig<StatefulRedisConnection<String, String>> poolConfig = new GenericObjectPoolConfig<>();
//        poolConfig.setMaxTotal(redisClientCount);
//        poolConfig.setMaxIdle(redisClientCount);
//        poolConfig.setMinIdle(redisClientCount);
//        poolConfig.setMaxWait(Duration.ofMillis(100));
//        return ConnectionPoolSupport.createGenericObjectPool(() -> redisClient.connect(), poolConfig);
//    }
//
//    @Bean
//    public CompletionStage<BoundedAsyncPool<StatefulRedisConnection<String, String>>> asyncPool() {
//        int redisClientCount = 2000;
//        return AsyncConnectionPoolSupport.createBoundedObjectPoolAsync(
//                () -> redisClient.connectAsync(StringCodec.UTF8, RedisURI.create(REDIS_CON_URL)), BoundedPoolConfig.builder().maxTotal(redisClientCount).maxIdle(
//                        redisClientCount).minIdle(redisClientCount).build());
//    }
}
