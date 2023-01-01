package minssogi.study;

import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class MailValidator {

    @SneakyThrows
    public static void main(String[] args) {

        String host = "smtp.naver.com";
        CompletableFuture<Boolean> emailValidationFuture = validateEmail(host, 587);

        Long start = System.currentTimeMillis();
        emailValidationFuture.whenComplete((result, e) -> {
            if (e == null) {
                Long end = System.currentTimeMillis();
                System.out.println(host + ":" + result + ":" + (end - start));
            } else {
                System.out.println("Occur Error : " + e.getMessage());
            }
        });

        Thread.sleep(1000);

//        start = System.currentTimeMillis();
//        host = "smtp.gmail.com";
//        result = smtpPortIsOpen(host, 587);
//        end = System.currentTimeMillis();
//        System.out.println(host + ":" + result + ":" + (end - start));
//
//
//        start = System.currentTimeMillis();
//        host = "helloworld.com.com";
//        result = smtpPortIsOpen(host, 587);
//        end = System.currentTimeMillis();
//        System.out.println(host + ":" + result + ":" + (end - start));
//
//        start = System.currentTimeMillis();
//        host = "smtp.daum.net";
//        result = smtpPortIsOpen(host, 587);
//        end = System.currentTimeMillis();
//        System.out.println(host + ":" + result + ":" + (end - start));
//
//        start = System.currentTimeMillis();
//        host = "smtp.qq.com";
//        result = smtpPortIsOpen(host, 587);
//        end = System.currentTimeMillis();
//        System.out.println(host + ":" + result + ":" + (end - start));
//
//        start = System.currentTimeMillis();
//        host = "smtp.navercorp.com";
//        result = smtpPortIsOpen(host, 587);
//        end = System.currentTimeMillis();
//        System.out.println(host + ":" + result + ":" + (end - start));
//
//        start = System.currentTimeMillis();
//        host = "stmp.inbound-mx.org";
//        result = smtpPortIsOpen(host, 587);
//        end = System.currentTimeMillis();
//        System.out.println(host + ":" + result + ":" + (end - start));
    }
    private static CompletableFuture<Boolean> validateEmail(String host, Integer port) {
        return smtpPortIsOpen(host, port).thenCombine(mxRecordCheck(host, port), (portIsOpen, mxRecordIsValid) -> {
            return portIsOpen && mxRecordIsValid;
        });
    }


    private static CompletableFuture<Boolean> mxRecordCheck(String host, Integer port) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(90);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return true;
        });
    }

    private static CompletableFuture<Boolean> smtpPortIsOpen(String host, Integer port) {
        return CompletableFuture.supplyAsync(() -> {
            Long start = System.currentTimeMillis();
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(host, port), 500);
                socket.close();

                System.out.println("smtpPortIsOpen tooks " + (System.currentTimeMillis() - start) + "ms");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });
    }
}
