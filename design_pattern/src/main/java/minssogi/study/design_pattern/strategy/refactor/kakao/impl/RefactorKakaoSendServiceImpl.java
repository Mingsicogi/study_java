package minssogi.study.design_pattern.strategy.refactor.kakao.impl;

import lombok.extern.slf4j.Slf4j;
import minssogi.study.design_pattern.strategy.SendTypeCd;
import minssogi.study.design_pattern.strategy.refactor.SendService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RefactorKakaoSendServiceImpl implements SendService {
    @Override
    public String send(String target, String message) {
        return String.format("### sendKakaoTalkMessage : target : %s | message : %s", target, message);
    }

    @Override
    public SendTypeCd support() {
        return SendTypeCd.KAKAO;
    }
}
