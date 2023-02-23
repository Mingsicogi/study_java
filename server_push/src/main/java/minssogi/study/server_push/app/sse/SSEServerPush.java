package minssogi.study.server_push.app.sse;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SSEServerPush {

    private final List<SseEmitter> sseEmitters;

    @GetMapping("/serverPush/sse")
    public String page() {
        return "sse_example";
    }

    @ResponseBody
    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect() {
        SseEmitter sseEmitter = new SseEmitter();
        sseEmitters.add(sseEmitter);
        return ResponseEntity.ok(sseEmitter);
    }

    @ResponseBody
    @PostMapping("/serverPush/sse/send")
    @SneakyThrows
    public ResponseEntity<String> send(@RequestBody Data data) {
        for (SseEmitter e : sseEmitters) {
            log.info("### {}", e.toString());
            e.send(SseEmitter.event().name("minssogi-web-push-sse").data(data, MediaType.APPLICATION_JSON));
        }
        return ResponseEntity.ok("title : " + data.title + " body : " + data.body + " send success!");
    }

    @lombok.Data
    public static class Data {
        String title;
        String body;
    }
}
