package minssogi.study.tsdb.app;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
public class ActiveSignalController {

    private final InfluxDBClient influxDBClient;

    @GetMapping("/report/activeSignal")
    public ResponseEntity<String> reportActiveSignal(@RequestParam("gameCd") String gameCd, @RequestParam("loginIdp") String loginIdp, @RequestParam("nid") String nid) {
        Point point = Point.measurement("ccu").addTag("gameCd", gameCd).addTag("loginIdp", loginIdp).addField("nid", nid).time(Instant.now(), WritePrecision.MS);
        influxDBClient.getWriteApiBlocking().writePoint(point);
        return ResponseEntity.ok("saved");
    }
}
