package minssogi.study.design_pattern.strategy.bad.email.impl;

import lombok.extern.slf4j.Slf4j;
import minssogi.study.design_pattern.strategy.bad.email.KakaoSendService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KakaoSendServiceImpl implements KakaoSendService {
    @Override
    public void sendKakaoTalkMessage(String target, String message) {
        log.info("### sendKakaoTalkMessage : target : {} | message : {}", target, message);
    }
}
