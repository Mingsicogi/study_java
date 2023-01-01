package minssogi.study.tsdb;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

class TsdbApplicationTests {

    @Test
    void contextLoads() {
        LocalDateTime now = LocalDateTime.now();
        int second = now.getSecond();
        long epochSecond = now.minusSeconds(second).toEpochSecond(ZoneOffset.UTC);

        System.out.println(epochSecond);
    }

}
