package minssogi.study.server_push.app.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebPush {

    private final FirebaseMessaging firebaseMessaging;

    @ResponseBody
    @GetMapping("/serverPush/fcm/send")
    public ResponseEntity<String> fcmWebPush(@RequestParam("msg") String msg) {
        Message message = Message.builder()
                .setTopic("minssogi-server-push-study")
                .putData("body", msg)
                .build();

        try {
            String logTrackingId = firebaseMessaging.send(message);
            log.debug("### logTrackingId : {} ###", logTrackingId);
        } catch (FirebaseMessagingException ignore) {}

        return ResponseEntity.ok(msg + " send success!");
    }

    @GetMapping("/serverPush/fcm")
    public String fcmPage() {
        return "fcm_example";
    }
}
