package minssogi.study.async.sample;

import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.AsyncWebRequest;
import org.springframework.web.context.request.async.StandardServletAsyncWebRequest;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.concurrent.Future;

/**
 * 여러 모듈로부터 http를 통해 데이터를 얻어와 merge 해 리턴해주는 경우
 *
 */
@RestController
public class Case1 {

    private RestTemplate restTemplate = new RestTemplate();
    private AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

    @GetMapping("/case1/callMultipleServerAndMergeResponse")
    public WebAsyncTask<String> callMultipleServerAndMergeResponse() {
        return new WebAsyncTask<>(10_000L, "async-test", () -> {

            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            StringBuilder result = new StringBuilder();

            //            asyncRestTemplate.getForEntity( "http://127.0.0.1:8080/case1/longTakeTimeTask", String.class).addCallback(success -> {
            //                assert success != null;
            //                if (success.getStatusCode().is2xxSuccessful()) {
            //                    result.append(success.getBody());
            //                } else {
            //                    result.append("[").append(success.getStatusCode()).append("]").append(success.getBody());
            //                }
            //                result.append("\n");
            //            }, fail -> result.append("Occur error while calling http://127.0.0.1:8080/case1/longTakeTimeTask | error message : ").append(fail.getMessage()).append("\n"));
            //
            //            asyncRestTemplate.getForEntity( "http://127.0.0.1:8080/case1/shortTakeTimeTask", String.class).addCallback(success -> {
            //                assert success != null;
            //                if (success.getStatusCode().is2xxSuccessful()) {
            //                    result.append(success.getBody());
            //                } else {
            //                    result.append("[").append(success.getStatusCode()).append("]").append(success.getBody());
            //                }
            //                result.append("\n");
            //            }, fail -> result.append("Occur error while calling http://127.0.0.1:8080/case1/shortTakeTimeTask | error message : ").append(fail.getMessage()).append("\n"));
            //
            //            asyncRestTemplate.getForEntity( "http://127.0.0.1:8080/case1/normalTakeTimeTask", String.class).addCallback(success -> {
            //                assert success != null;
            //                if (success.getStatusCode().is2xxSuccessful()) {
            //                    result.append(success.getBody());
            //                } else {
            //                    result.append("[").append(success.getStatusCode()).append("]").append(success.getBody());
            //                }
            //                result.append("\n");
            //            }, fail -> result.append("Occur error while calling http://127.0.0.1:8080/case1/normalTakeTimeTask | error message : ").append(fail.getMessage()).append("\n"));

            ListenableFuture<ResponseEntity<String>> forEntity1 = asyncRestTemplate.getForEntity("http://127.0.0.1:8080/case1/longTakeTimeTask", String.class);
            ListenableFuture<ResponseEntity<String>> forEntity2 = asyncRestTemplate.getForEntity("http://127.0.0.1:8080/case1/shortTakeTimeTask", String.class);
            ListenableFuture<ResponseEntity<String>> forEntity3 = asyncRestTemplate.getForEntity("http://127.0.0.1:8080/case1/normalTakeTimeTask", String.class);

            result.append(forEntity1.get().getBody()).append(forEntity2.get().getBody()).append(forEntity3.get().getBody());

            stopWatch.stop();

            result.append("\ntotal : " + stopWatch.getTotalTimeMillis());
            return result.toString();
        });
    }
}
