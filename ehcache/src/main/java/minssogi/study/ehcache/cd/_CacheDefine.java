package minssogi.study.ehcache.cd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import minssogi.study.ehcache.app.ProductRepository;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static minssogi.study.ehcache.utils.SpringContainerUtil.getBean;

@AllArgsConstructor
@Getter
public enum _CacheDefine {
    PRODUCT_CACHE("productCache", 1, TimeUnit.SECONDS, key -> getBean(ProductRepository.class).findProductByProductName((String)key))

    ;

    private String cacheName;
    private int ttl;
    private TimeUnit ttlUnit;
    private Function<Object, Object> dataLoadFunction;
}
