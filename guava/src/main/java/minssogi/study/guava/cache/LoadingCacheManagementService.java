package minssogi.study.guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
public class LoadingCacheManagementService {

    private final Map<String, LoadingCache<Object, Object>> CACHE_STORAGE = new HashMap<>();

    public <K, V> LoadingCache<K, V> getCache(String cacheName) {
        LoadingCache<Object, Object> cache = CACHE_STORAGE.get(cacheName);
        commonValidated(cacheName, cache);

        return (LoadingCache<K, V>) cache;
    }

    private void commonValidated(String cacheName, LoadingCache<Object, Object> cache) {
        if (cache == null) {
            throw new NullPointerException("Not exists cache | cacheName : " + cacheName);
        }
    }

    public <K, V> LoadingCache<K, V> createCache(String cacheName, CacheRefreshStrategy strategy, Function<K, V> dataLoadFunction) {
        return this.createCache(cacheName, strategy, 100, 1, TimeUnit.MINUTES, dataLoadFunction, Executors.newFixedThreadPool(1));
    }

    public <K, V> LoadingCache<K, V> createCache(String cacheName, CacheRefreshStrategy strategy, Function<K, V> dataLoadFunction, Executor dataLoadFunctionExecutor) {
        return this.createCache(cacheName, strategy, 100, 1, TimeUnit.MINUTES, dataLoadFunction, dataLoadFunctionExecutor);
    }

    public <K, V> LoadingCache<K, V> createCache(String cacheName, CacheRefreshStrategy strategy, int maximumSize, long duration, TimeUnit timeUnit,
                                                 Function<K, V> dataLoadFunction) {
        return this.createCache(cacheName, strategy, maximumSize, duration, timeUnit, dataLoadFunction, Executors.newFixedThreadPool(1));
    }

    private <K, V> LoadingCache<K, V> createCache(String cacheName, CacheRefreshStrategy strategy, int maximumSize, long duration, TimeUnit timeUnit,
                                                  Function<K, V> dataLoadFunction, Executor dataLoadFunctionExecutor) {
        CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder().recordStats().maximumSize(maximumSize);

        if (strategy == CacheRefreshStrategy.REFRESH) {
            LoadingCache<Object, Object> newCache = builder.refreshAfterWrite(duration, timeUnit).build(new CacheLoader<>() {
                @Override
                public ListenableFuture<Object> reload(Object key, Object oldValue) throws Exception {
                    ListenableFutureTask<V> task = ListenableFutureTask.create(() -> dataLoadFunction.apply((K)key));
                    dataLoadFunctionExecutor.execute(task);
                    return (ListenableFuture<Object>) task;
                }

                @Override
                public Object load(Object key) throws Exception {
                    return dataLoadFunction.apply((K)key);
                }
            });

            CACHE_STORAGE.put(cacheName, newCache);

            return (LoadingCache<K, V>) newCache;
        } else {
            throw new IllegalArgumentException(strategy.name() + " is not support strategy yet.");
        }
    }

    public String cacheInformation(String cacheName) {
        LoadingCache<Object, Object> cache = CACHE_STORAGE.get(cacheName);
        commonValidated(cacheName, cache);

        return cache.stats().toString();
    }

    public CacheRefreshStrategy getRefreshStrategy() {
        return CacheRefreshStrategy.REFRESH;
    }

    protected enum CacheRefreshStrategy {
        REFRESH,
        EXPIRE

        ;
    }
}
