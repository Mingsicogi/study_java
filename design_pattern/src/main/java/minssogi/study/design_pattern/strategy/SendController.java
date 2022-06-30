package minssogi.study.design_pattern.strategy;

import lombok.RequiredArgsConstructor;
import minssogi.study.design_pattern.strategy.bad.email.EmailSendService;
import minssogi.study.design_pattern.strategy.bad.kakao.KakaoSendService;
import minssogi.study.design_pattern.strategy.bad.line.LINESendService;
import minssogi.study.design_pattern.strategy.bad.sms.SmsSendService;
import minssogi.study.design_pattern.strategy.refactor.SendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.GenericWebApplicationContext;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class SendController {

    private final EmailSendService emailSendService;
    private final KakaoSendService kakaoSendService;
    private final LINESendService lineSendService;
    private final SmsSendService smsSendService;

    /**
     * test link
     *  - http://localhost:8087/design_pattern/strategy/bad?send_type=EMAIL&send_target=minssogi@line.games&send_message=hello~
     *
     * @param sendTypeCd
     * @param sendTarget
     * @param sendMessage
     * @return
     */
    @GetMapping("/design_pattern/strategy/bad")
    public ResponseEntity<String> strategyPattern_bad(@RequestParam("send_type") SendTypeCd sendTypeCd, @RequestParam("send_target") String sendTarget,
                                                  @RequestParam("send_message") String sendMessage) {
        switch (sendTypeCd) {
            case EMAIL:
                return ResponseEntity.ok(emailSendService.sendEmail(sendTarget, sendMessage));
            case KAKAO:
                return ResponseEntity.ok(kakaoSendService.sendKakaoTalkMessage(sendTarget, sendMessage));
            case LINE:
                return ResponseEntity.ok(lineSendService.sendLINEMessage(sendTarget, sendMessage));
            case SMS:
                return ResponseEntity.ok(smsSendService.sendSms(sendTarget, sendMessage));
            default:
                throw new IllegalArgumentException("Not support...");
        }
    }

    private final GenericWebApplicationContext context;

    /**
     * test link
     *  - http://localhost:8087/design_pattern/strategy/refactor?send_type=EMAIL&send_target=minssogi@line.games&send_message=hello~
     *
     * @param sendTypeCd
     * @param sendTarget
     * @param sendMessage
     * @return
     */
    @GetMapping("/design_pattern/strategy/refactor")
    public ResponseEntity<String> strategyPattern_refactor(@RequestParam("send_type") SendTypeCd sendTypeCd, @RequestParam("send_target") String sendTarget,
                                                  @RequestParam("send_message") String sendMessage) {

        Map<String, SendService> beansOfType = context.getBeansOfType(SendService.class);

        AtomicReference<String> sendResult = new AtomicReference<>("");
        beansOfType.forEach((beanName, sendService) -> {
            if (sendTypeCd == sendService.support()) {
                sendResult.set(sendService.send(sendTarget, sendMessage));
            }
        });

        if ("".equals(sendResult.get())) {
            throw new IllegalArgumentException("Not support...");
        }

        return ResponseEntity.ok(sendResult.get());
    }
}
