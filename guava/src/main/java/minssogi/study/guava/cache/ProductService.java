package minssogi.study.guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ProductService {

    private final LoadingCache<String, Product> productCache;
    private final ProductRepository productRepository;

    private final Executor executor = Executors.newFixedThreadPool(100);

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.productCache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .refreshAfterWrite(1, TimeUnit.MILLISECONDS)
                .build(new CacheLoader<>() {
                    ListenableFutureTask<Product> task = null;

                    @NotNull
                    @Override
                    public Product load(@NotNull String productName) {
                        return productRepository.findProductByProductName(productName);
                    }

                    @Override
                    public ListenableFuture<Product> reload(String productName, Product oldValue) throws Exception {
                        if (task == null) {
                            task = ListenableFutureTask.create(() -> productRepository.findProductByProductName(productName));
                        } else if (!task.isDone()) {
                            return Futures.immediateFuture(oldValue);
                        }

                        executor.execute(task);
                        return task;
                    }
                });
    }

    public Product getProductWithCache(String productName) {
        try {
            return productCache.get(productName);
        } catch (ExecutionException e) {
            log.error("### Select from database because of guava cache error in execution", e);
            return productRepository.findProductByProductName(productName);
        }
    }
}
