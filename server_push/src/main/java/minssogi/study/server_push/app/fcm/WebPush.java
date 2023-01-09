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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebPush {

    private final FirebaseMessaging firebaseMessaging;
    private final Set<String> FCM_WEB_TOKEN_STORAGE = new HashSet<>();

    @ResponseBody
    @GetMapping("/serverPush/fcm/send")
    public ResponseEntity<String> fcmWebPush(@RequestParam("msg") String msg) {
        for (String webToken : FCM_WEB_TOKEN_STORAGE) {
            Message message = Message.builder()
                    .setToken(webToken)
                    .putData("body", msg)
                    .putData("title", "minssogi-web-push-fcm")
                    .build();

            try {
                String logTrackingId = firebaseMessaging.send(message);
                log.info("### logTrackingId : {} ###", logTrackingId);
            } catch (FirebaseMessagingException ignore) {ignore.printStackTrace();}
        }

        return ResponseEntity.ok(msg + " send success!");
    }

    @GetMapping("/serverPush/fcm/saveToken")
    public ResponseEntity<String> fcmTokenSave(@RequestParam("token") String token) {
        FCM_WEB_TOKEN_STORAGE.add(token);
        return ResponseEntity.ok(token + " save success!");
    }

    @GetMapping("/serverPush/fcm")
    public String fcmPage() {
        return "fcm_example";
    }
}
