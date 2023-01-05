package minssogi.study.server_push.app.websocket;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import static minssogi.study.server_push.app.websocket.MyHandler.SESSION_REPOSITORY;

@Controller
public class WebSocketServerPush {

    @GetMapping("/serverPush/ws")
    public ResponseEntity<String> serverPush(@RequestParam("session_id") String sessionId, @RequestParam("push_data") String pushData) throws Exception {
        WebSocketSession webSocketSession = SESSION_REPOSITORY.get(sessionId);
        webSocketSession.sendMessage(new TextMessage(pushData));
        return ResponseEntity.ok("push success!!");
    }
}
