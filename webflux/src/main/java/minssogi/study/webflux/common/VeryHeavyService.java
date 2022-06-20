package minssogi.study.webflux.common;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class VeryHeavyService {

    @SneakyThrows
    public Mono<String> getDataFromGoogleServer() {
        return Mono.fromSupplier(() -> {
            String taskTime = RandomStringUtils.randomNumeric(4);
            try {
                log.info("### START getDataFromGoogleServer | thread : {} ###", Thread.currentThread().getName());
                Thread.sleep(Integer.parseInt(taskTime));
                log.info("### END getDataFromGoogleServer | thread : {} ###", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "getDataFromGoogleServer takes " + taskTime + "ms";
        });
    }

    @SneakyThrows
    public Mono<String> getDataFromFacebookServer() {
        return Mono.fromSupplier(() -> {
            String taskTime = RandomStringUtils.randomNumeric(4);
            try {
                log.info("### START getDataFromFacebookServer | thread : {} ###", Thread.currentThread().getName());
                Thread.sleep(Integer.parseInt(taskTime));
                log.info("### END getDataFromFacebookServer | thread : {} ###", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "getDataFromFacebookServer takes " + taskTime + "ms";
        });
    }

    @SneakyThrows
    public Mono<String> getDataFromAppleServer() {
        return Mono.fromSupplier(() -> {
            String taskTime = RandomStringUtils.randomNumeric(4);
            try {
                log.info("### START getDataFromAppleServer | thread : {} ###", Thread.currentThread().getName());
                Thread.sleep(Integer.parseInt(taskTime));
                log.info("### END getDataFromAppleServer | thread : {} ###", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "getDataFromAppleServer takes " + taskTime + "ms";
        });
    }
}
