package minssogi.study.design_pattern.strategy.bad.sms.impl;

import lombok.extern.slf4j.Slf4j;
import minssogi.study.design_pattern.strategy.bad.sms.SmsSendService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SmsSendServiceImpl implements SmsSendService {
    @Override
    public String sendSms(String target, String message) {
        return String.format("### sendSms : target : %s | message : %s", target, message);
    }
}
