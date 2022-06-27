package minssogi.study.design_pattern.strategy.bad.email;

public interface EmailSendService {
    String sendEmail(String target, String message);
}
