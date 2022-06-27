package minssogi.study.design_pattern.strategy.bad.sms;

public interface SmsSendService {
    String sendSms(String target, String message);
}
