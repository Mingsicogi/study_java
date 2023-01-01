package minssogi.study.tsdb.app;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ActiveSignalControllerTest {

    private final InfluxDBClient influxDBClient;

    private final List<String> games = Arrays.asList("UD", "EH", "UWO", "UDG", "UWO", "UDG", "UWO", "UDG", "UDG", "UDG", "UWO", "UDG");
    private final List<String> idps = Arrays.asList("FLOOR", "GOOGLE", "FACEBOOK", "APPLE", "STREAM", "STREAM", "STREAM", "GOOGLE", "GOOGLE", "FLOOR", "FLOOR", "FLOOR");

    private final Integer testCount = 100;

    private Integer currentTotalActiveThreadCount = 0;

    @SneakyThrows
    @GetMapping("/activeSignal/testStart")
    public ResponseEntity<String> activeSignalTestStart() {
        currentTotalActiveThreadCount += testCount;

        for (int i = 0; i < testCount; i++) {
            Runnable runnable = () -> reportActiveSignal();
            Thread t = new Thread(runnable);
            t.start();
        }

        return ResponseEntity.ok("Start reportActiveSignal " + testCount + " | total active thread count : " + currentTotalActiveThreadCount);
    }

    @SneakyThrows
    private void reportActiveSignal() {
        String strGnid = RandomStringUtils.randomNumeric(8);
        Integer gnid = Integer.parseInt(strGnid);
        String gameCd = games.get(Integer.parseInt(RandomStringUtils.randomNumeric(1)));
        String loginIdp = idps.get(Integer.parseInt(RandomStringUtils.randomNumeric(1)));

        Point point = Point.measurement("ccu")
                .addTag("gameCd", gameCd)
                .addTag("loginIdp", loginIdp)
                .addTag("gnid", strGnid)
                .addField("gnid", gnid)
                .addField("gameCd", gameCd)
                .addField("loginIdp", loginIdp)
                ;

        while (true) {
//            LocalDateTime now = LocalDateTime.now();
//            int second = now.getSecond();
//            long epochSecond = now.minusSeconds(second).toEpochSecond(ZoneOffset.UTC);
//            point.time(epochSecond, WritePrecision.S);
            point.time(Instant.now(), WritePrecision.S);
            influxDBClient.getWriteApiBlocking().writePoint(point);
            Thread.sleep(1000);
        }
    }
}
