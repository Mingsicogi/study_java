package minssogi.study.design_pattern.strategy.refactor.email.impl;

import lombok.extern.slf4j.Slf4j;
import minssogi.study.design_pattern.strategy.SendTypeCd;
import minssogi.study.design_pattern.strategy.refactor.SendService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RefactorEmailSendServiceImpl implements SendService {

    @Override
    public String send(String target, String message) {
        return String.format("### sendEmail : target : %s | message : %s", target, message);
    }

    @Override
    public SendTypeCd support() {
        return SendTypeCd.EMAIL;
    }
}
