package minssogi.study.resilience4j.example;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Recruit {

    private boolean isError = false;

    @CircuitBreaker(name = "recruitPage", fallbackMethod = "fallbackRecruitMain")
    @GetMapping("/recruit")
    public String recruitMain() {
        if (isError) {
            throw new RuntimeException();
        }

        return "recruit";
    }

    public String fallbackRecruitMain(RuntimeException e) {
        return "recruit_fallback";
    }

    @Scheduled(fixedRate = 5000L)
    public void errorFlagSwitch() {
        isError = !isError;
    }
}
