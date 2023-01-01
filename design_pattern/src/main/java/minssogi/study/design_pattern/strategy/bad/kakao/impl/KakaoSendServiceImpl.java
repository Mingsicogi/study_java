package minssogi.study.design_pattern.strategy.bad.kakao.impl;

import lombok.extern.slf4j.Slf4j;
import minssogi.study.design_pattern.strategy.bad.kakao.KakaoSendService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KakaoSendServiceImpl implements KakaoSendService {
    @Override
    public String sendKakaoTalkMessage(String target, String message) {
        return String.format("### sendKakaoTalkMessage : target : %s | message : %s", target, message);
    }
}
