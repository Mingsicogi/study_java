package minssogi.study.webflux.mono;

import lombok.RequiredArgsConstructor;
import minssogi.study.webflux.common.VeryHeavyService;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/mono")
@RequiredArgsConstructor
public class MonoStudy {

    private final VeryHeavyService veryHeavyService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(100);

    @GetMapping("/test1")
    public Mono<String> test1() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        LocalDateTime now = LocalDateTime.now();
        return Mono.from(veryHeavyService.getDataFromFacebookServer())
                .publishOn(Schedulers.fromExecutor(executorService))
                .zipWith(veryHeavyService.getDataFromAppleServer())
                .publishOn(Schedulers.fromExecutor(executorService))
                .map(result -> result.getT1() + " | " + result.getT2())
                .zipWith(veryHeavyService.getDataFromGoogleServer())
                .publishOn(Schedulers.fromExecutor(executorService))
                .map(result -> {
                    String finalResult = result.getT1() + " | " + result.getT2();
                    stopWatch.stop();
                    finalResult += " | Totally takes " + stopWatch.getTotalTimeSeconds() + "s";

                    return "Start time : " + now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " | " + finalResult;
                });
    }

}
