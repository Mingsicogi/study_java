package minssogi.study.rate_limiting_webflux.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.codec.StringCodec;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.support.AsyncConnectionPoolSupport;
import io.lettuce.core.support.BoundedAsyncPool;
import io.lettuce.core.support.BoundedPoolConfig;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.CompletionStage;

@EnableAsync
@Configuration
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class RedisConfiguration {

//    private final String REDIS_HOST = "localhost";
    private final String REDIS_HOST = "127.0.0.1"; // network latency를 늘리기 위해 AWS EC2에 Redis를 설치
    private final int REDIS_PORT = 6379;
    private final String REDIS_CON_URL = "redis://" + REDIS_HOST + ":" + REDIS_PORT;

//    @Bean
//    public RedisCommands<String, String> redisCommands() {
//        ClientResources nettyClient = ClientResources.builder()
//                .ioThreadPoolSize(2)
//                .computationThreadPoolSize(2)
//                .eventExecutorGroup(new NioEventLoopGroup(2, new DefaultThreadFactory("minssogi-async-executor-thread")))
////                .eventLoopGroupProvider(new DefaultEventLoopGroupProvider(1))
//                .build();
//
//        RedisClient redisClient = RedisClient.create(nettyClient, REDIS_CON_URL);
//
////        RedisClient redisClient = RedisClient.create(REDIS_CON_URL);
//        redisClient.setOptions(ClientOptions.builder().timeoutOptions(TimeoutOptions.builder().fixedTimeout(Duration.ofMillis(10_000)).build()).build());
//
//        return redisClient.connect().sync();
//    }

//    @Bean
//    public RedisAsyncCommands<String, String> asyncRedisCommands() {
//        ClientResources nettyClient = ClientResources.builder()
//                .ioThreadPoolSize(2)
//                .eventExecutorGroup(new NioEventLoopGroup(2, new DefaultThreadFactory("minssogi-async-executor-thread")))
////                .eventLoopGroupProvider(new DefaultEventLoopGroupProvider(1))
//                .build();
//
//        RedisClient redisClient = RedisClient.create(nettyClient, REDIS_CON_URL);
//
////        RedisClient redisClient = RedisClient.create(REDIS_CON_URL);
//        redisClient.setOptions(ClientOptions.builder().timeoutOptions(TimeoutOptions.builder().fixedTimeout(Duration.ofMillis(10_000)).build()).build());
//
//        return redisClient.connect().async();
//    }

//    @Bean
//    public GenericObjectPool<StatefulRedisConnection<String, String>> syncPool() {
//        ClientResources nettyClient = ClientResources.builder()
//                .ioThreadPoolSize(8)
//                .computationThreadPoolSize(8)
//                .eventExecutorGroup(new NioEventLoopGroup(8, new DefaultThreadFactory("minssogi-async-executor-thread")))
////                .eventLoopGroupProvider(new DefaultEventLoopGroupProvider(1))
//                .build();
//
//        RedisClient redisClient = RedisClient.create(nettyClient, REDIS_CON_URL);
//
//        int redisClientCount = 10;
//        GenericObjectPoolConfig<StatefulRedisConnection<String, String>> poolConfig = new GenericObjectPoolConfig<>();
//        poolConfig.setMaxTotal(redisClientCount);
//        poolConfig.setMaxIdle(redisClientCount);
//        poolConfig.setMinIdle(redisClientCount);
//        poolConfig.setMaxWait(Duration.ofMillis(100));
//        return ConnectionPoolSupport.createGenericObjectPool(() -> redisClient.connect(), poolConfig);
//    }
//
    @Bean
    public CompletionStage<BoundedAsyncPool<StatefulRedisConnection<String, String>>> asyncPool() {
        ClientResources nettyClient = ClientResources.builder()
                .ioThreadPoolSize(12)
                .computationThreadPoolSize(12)
                .eventExecutorGroup(new NioEventLoopGroup(12, new DefaultThreadFactory("minssogi-async-executor-thread")))
//                .eventLoopGroupProvider(new DefaultEventLoopGroupProvider(1))
                .build();

        RedisClient redisClient = RedisClient.create(nettyClient, REDIS_CON_URL);

        int redisClientCount = 200;
        return AsyncConnectionPoolSupport.createBoundedObjectPoolAsync(
                () -> redisClient.connectAsync(StringCodec.UTF8, RedisURI.create(REDIS_CON_URL)), BoundedPoolConfig.builder().maxTotal(redisClientCount).maxIdle(
                        redisClientCount).minIdle(redisClientCount).build());
    }

}
