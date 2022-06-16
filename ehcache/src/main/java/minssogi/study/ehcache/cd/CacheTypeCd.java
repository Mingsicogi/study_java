package minssogi.study.ehcache.cd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import minssogi.study.ehcache.app.ProductService;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static minssogi.study.ehcache.utils.SpringContainerUtil.getBean;

@AllArgsConstructor
@Getter
public enum CacheTypeCd {

    PRODUCT_CACHE("productCache", 1, TimeUnit.SECONDS, key -> getBean(ProductService.class).getProductNoCache((String)key))

    ;

    private String cacheName;
    private int ttl;
    private TimeUnit ttlUnit;
    private Function<Object, Object> dataLoadFunction;
}
