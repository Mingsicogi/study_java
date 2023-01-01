package minssogi.study.guava.cache;

import com.google.common.cache.LoadingCache;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@SpringBootTest
class LoadingCacheManagementServiceTest {

    @Autowired
    LoadingCacheManagementService loadingCacheManagementService;

    private final List<TestEntity> dummyDataList = Arrays.asList(
            new TestEntity(1, "aa-1", 5000), new TestEntity(6, "ff-6", 9000),
            new TestEntity(2, "bb-2", 15000), new TestEntity(7, "gg-7", 5100),
            new TestEntity(3, "cc-3", 25000), new TestEntity(8, "hh-8", 5200),
            new TestEntity(4, "dd-4", 35000), new TestEntity(9, "ii-9", 1200),
            new TestEntity(5, "ee-5", 45000), new TestEntity(10, "jj-10", 51000)
    );

    @SneakyThrows
    @Test
    void createCache() {
        String cacheName = "testCache";
        LoadingCache<Integer, TestEntity> testCache = loadingCacheManagementService.createCache(cacheName, loadingCacheManagementService.getRefreshStrategy(), findTestFunction());

        TestEntity testEntity1 = testCache.get(1);
        TestEntity testEntity2 = testCache.get(2);
        TestEntity testEntity3 = testCache.get(3);

        Assertions.assertEquals(dummyDataList.get(1).getId(), testEntity1.id);
        Assertions.assertEquals(dummyDataList.get(2).getId(), testEntity2.id);
        Assertions.assertEquals(dummyDataList.get(3).getId(), testEntity3.id);

        testCache.asMap().forEach((key, value) -> {
            System.out.println(key + " : " + value);
        });
        System.out.println(loadingCacheManagementService.cacheInformation(cacheName));
    }

    @Test
    void getCache() {

    }

    private Function<Integer, TestEntity> findTestFunction() {
//        if (id >= dummyDataList.size()) {
//            throw new NullPointerException("Not exists data.");
//        } else if (id <= 0) {
//            throw new IllegalArgumentException("Invalid id.");
//        }

        return dummyDataList::get;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    private class TestEntity {
        private int id;
        private String productName;
        private int price;
    }
}