package minssogi.study.design_pattern.strategy.bad.line.impl;

import lombok.extern.slf4j.Slf4j;
import minssogi.study.design_pattern.strategy.bad.line.LINESendService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LINESendServiceImpl implements LINESendService {
    @Override
    public String sendLINEMessage(String target, String message) {
        return String.format("### sendLINEMessage : target : %s | message : %s", target, message);
    }
}
