package minssogi.study.design_pattern.strategy.refactor;

import minssogi.study.design_pattern.strategy.SendTypeCd;

public interface SendService {

    String send(String sendTarget, String sendMssage);

    SendTypeCd support();
}
