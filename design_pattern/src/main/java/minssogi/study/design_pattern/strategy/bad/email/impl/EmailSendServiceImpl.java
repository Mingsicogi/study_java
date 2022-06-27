package minssogi.study.design_pattern.strategy.bad.email.impl;

import lombok.extern.slf4j.Slf4j;
import minssogi.study.design_pattern.strategy.bad.email.EmailSendService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailSendServiceImpl implements EmailSendService {

    @Override
    public String sendEmail(String target, String message) {
        return String.format("### sendEmail : target : %s | message : %s", target, message);
    }
}
