package minssogi.study.webflux.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class WebClientConfigurationTest {

    private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:8086/flux/test1")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .clientConnector(new ReactorClientHttpConnector(HttpClient.create().responseTimeout(Duration.ofSeconds(1))))
        .build();

    @Test
    @SneakyThrows
    @Disabled
    void webClient() {
        Mono<String> response = webClient.get().retrieve().bodyToMono(String.class);

        String result = response.block();

        log.info("### {} ###", result);
    }
}