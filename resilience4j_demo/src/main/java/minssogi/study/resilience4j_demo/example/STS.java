package minssogi.study.resilience4j_demo.example;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Controller
public class STS {

    @SneakyThrows
    @GetMapping("/sts/login/req")
    @RateLimiter(name = "stsLoginReq", fallbackMethod = "fallbackStsLoginReq")
    public String stsLoginReq() {
        Thread.sleep(1000);
        return "floor_front";
    }

    private String fallbackStsLoginReq(Throwable e) {
        log.info("### fallbackStsLoginReq error message : {}", e.getMessage());
        return "loginWaiting";
    }
}
