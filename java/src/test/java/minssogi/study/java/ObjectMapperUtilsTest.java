package minssogi.study.java;

import com.fasterxml.jackson.databind.ObjectMapper;
import minssogi.study.java.seventeen.ObjectMapperUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class ObjectMapperUtilsTest {

    final static ObjectMapper objectMapper = ObjectMapperUtils.createGeneralObjectMapper();

    @Test
    public void test1() {
        String yyyyMMddTHHmmssX = "2021-08-31T00:00:00Z";
        Instant instant = objectMapper.convertValue(yyyyMMddTHHmmssX, Instant.class);

        System.out.println(instant);
    }

    @Test
    public void test2() {
        String yyyyMMddTHHmmssSX = "2021-08-31T00:00:00.1Z";
        Instant instant = objectMapper.convertValue(yyyyMMddTHHmmssSX, Instant.class);

        System.out.println(instant);
    }
    @Test
    public void test4() {
        String yyyyMMddTHHmmssSX = "2021-08-31T00:00:00.0Z";
        Instant instant = objectMapper.convertValue(yyyyMMddTHHmmssSX, Instant.class);

        System.out.println(instant);
    }

    @Test
    public void test3() {
        String yyyyMMddTHHmmssSSSX = "2021-08-31T00:00:00.123Z";
        Instant instant = objectMapper.convertValue(yyyyMMddTHHmmssSSSX, Instant.class);

        System.out.println(instant);
    }
    @Test
    public void test7() {
        String yyyyMMddTHHmmssSX = "2021-08-31T00:00:00.000Z";
        Instant instant = objectMapper.convertValue(yyyyMMddTHHmmssSX, Instant.class);

        System.out.println(instant);
    }
    @Test
    public void test8() {
        String yyyyMMddTHHmmssSX = "2021-08-31T00:00:00.010Z";
        Instant instant = objectMapper.convertValue(yyyyMMddTHHmmssSX, Instant.class);

        System.out.println(instant);
    }



    @Test
    public void test5() {
        String yyyyMMddTHHmmssSX = "2021-08-31T00:00:00.11Z";
        Instant instant = objectMapper.convertValue(yyyyMMddTHHmmssSX, Instant.class);

        System.out.println(instant);
    }
    @Test
    public void test6() {
        String yyyyMMddTHHmmssSX = "2021-08-31T00:00:00.10Z";
        Instant instant = objectMapper.convertValue(yyyyMMddTHHmmssSX, Instant.class);

        System.out.println(instant);
    }
    @Test
    public void test9() {
        String yyyyMMddTHHmmssSX = "2021-08-31T00:00:00.01Z";
        Instant instant = objectMapper.convertValue(yyyyMMddTHHmmssSX, Instant.class);

        System.out.println(instant);
    }


    @Test
    public void test10() {
        String yyyyMMddTHHmmssX = "2021-08-31 12:23";
        Instant instant = objectMapper.convertValue(yyyyMMddTHHmmssX, Instant.class);

        System.out.println(instant);
    }

    @Test
    public void test11() {
        String yyyyMMddTHHmmssX = "2021-08-31 12:23:22";
        Instant instant = objectMapper.convertValue(yyyyMMddTHHmmssX, Instant.class);

        System.out.println(instant);
    }

    @Test
    public void test12() {
        String yyyyMMddTHHmmssX = "2021-08-31 12:23:00";
        Instant instant = objectMapper.convertValue(yyyyMMddTHHmmssX, Instant.class);

        System.out.println(instant);
    }

    @Test
    public void test13() {
        String yyyyMMddTHHmmssX = "2021-08-31 12:23:12.0";
        Instant instant = objectMapper.convertValue(yyyyMMddTHHmmssX, Instant.class);

        System.out.println(instant);
    }

    @Test
    public void test14() {
        String yyyyMMddTHHmmssX = "2021-08-31 12:23:00.0";
        Instant instant = objectMapper.convertValue(yyyyMMddTHHmmssX, Instant.class);

        System.out.println(instant);
    }

    @Test
    public void test15() {
        String yyyyMMddTHHmmssX = "2021-08-31 12:23:00.1";
        Instant instant = objectMapper.convertValue(yyyyMMddTHHmmssX, Instant.class);

        System.out.println(instant);
    }

    @Test
    public void test16() {
        String yyyyMMddTHHmmssX = "1234567890.123456789";
        Instant instant = objectMapper.convertValue(yyyyMMddTHHmmssX, Instant.class);

        System.out.println(instant);
    }

    @Test
    public void test17() {
        String yyyyMMddTHHmmssX = "1234567890.1289222";
        Instant instant = objectMapper.convertValue(yyyyMMddTHHmmssX, Instant.class);

        System.out.println(instant);
    }

    @Test
    public void test18() {
        String yyyyMMddTHHmmssX = "1234567890.0";
        Instant instant = objectMapper.convertValue(yyyyMMddTHHmmssX, Instant.class);

        System.out.println(instant);
    }

    @Test
    public void test19() {
        String yyyyMMddTHHmmssX = "1234567890";
        Instant instant = objectMapper.convertValue(yyyyMMddTHHmmssX, Instant.class);

        System.out.println(instant);
    }

    @Test
    public void test20() {
        String yyyyMMddTHHmmssX = "1234567890.";
        Instant instant = objectMapper.convertValue(yyyyMMddTHHmmssX, Instant.class);

        System.out.println(instant);
    }

    @Test
    public void test21() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            String yyyyMMddTHHmmssX = "12345678T90.";
            objectMapper.convertValue(yyyyMMddTHHmmssX, Instant.class);
        });
    }

    @Test
    public void test22() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            String yyyyMMddTHHmmssX = "1234567890.sdf";
            objectMapper.convertValue(yyyyMMddTHHmmssX, Instant.class);
        });
    }
}
