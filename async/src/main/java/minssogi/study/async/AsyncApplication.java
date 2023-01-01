package minssogi.study.async;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@SpringBootApplication
public class AsyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsyncApplication.class, args);
    }

    @Configuration
    public static class FilterConfig {

        @Bean
        public FilterRegistrationBean<CheckFilter> registrationBean() {
            FilterRegistrationBean<CheckFilter> filter = new FilterRegistrationBean<>();
            filter.setFilter(new CheckFilter());
            return filter;
        }

        @Bean(name = "async-test")
        public ThreadPoolTaskExecutor mvcAsyncTaskExecutor() {
            ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor(); //참고: https://keichee.tistory.com/m/382

            //te.setPrestartAllCoreThreads(true); //어플리케이션 시작될 때 CorePoolSize만큼 스레드를 미리 시작(배포 직후 부하가 많이 걸릴 경우를 대비)

            te.setCorePoolSize(100); //동시 실행시킬 수 있는 스레드 갯수(AllowCoreThreadTimeOut 값이 false이면, 생성된 후 해당 갯수만큼 스레드는 유지됨)
            te.setQueueCapacity(100); //쓰레드 풀 큐의 사이즈. corePoolSize 개수를 넘어설 때 큐에 해당 task들이 쌓이게 됨.최대로 maxPoolSize 개수 만큼 쌓일 수 있음
            te.setMaxPoolSize(500); //스레드풀의 최대 사이즈

            //어플리케이션 성격에 맞게 설정 수정
            te.setAllowCoreThreadTimeOut(true); //쓰레드가 유휴상태로 있을 때, keepAliveTime 만큼 기다렸다가 죽을지 여부
            te.setKeepAliveSeconds(60); //스레드풀의 스레드가 죽을 때까지의 대기시간

            te.setThreadNamePrefix("async-test");

            te.initialize();

            return te;
        }

        @Slf4j
        public static class CheckFilter implements Filter {

            public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
                threadLocal.set("Bye");

                filterChain.doFilter(servletRequest, servletResponse);

                if (servletRequest.isAsyncStarted()){
                    log.debug("### [{}] async called CheckFilter", Thread.currentThread().getName());
                } else {
                    log.debug("### [{}] called CheckFilter", Thread.currentThread().getName());
                }
            }
        }
    }

    @Slf4j
    @RestController
    public static class Receiver {

        @SneakyThrows
        @GetMapping("/receive")
        public String receive(@RequestParam("data") String data) {
            log.debug("### [{}] called /receive", Thread.currentThread().getName());
            Thread.sleep(3000);

            ExecutorService executorService = Executors.newFixedThreadPool(100);
            executorService.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " [async] request ip : " + getCurrentRequest().getRequestURI());
            });


            log.debug("### [{}] data : {} | thread local : {} | request ip  : {}", Thread.currentThread().getName(), data, FilterConfig.CheckFilter.threadLocal.get(), getCurrentRequest().getRequestURI());
            return "received data : " + data;
        }

        @SneakyThrows
        @GetMapping("/async/receive")
        public WebAsyncTask<String> asyncReceive(@RequestParam("data") String data) {
            log.debug("### [{}] called /async/receive", Thread.currentThread().getName());
            Thread.sleep(1500);
//            ExecutorService executorService = Executors.newFixedThreadPool(1);
//            executorService.submit(() -> {
//                System.out.println(Thread.currentThread().getName() + " [async] request ip : " + getCurrentRequest().getRequestURI());
//            });

            return new WebAsyncTask<>(5_000L, "async-test", () -> {
                log.debug("### [{}] data : {} | thread local : {} | request ip : {}", Thread.currentThread().getName(), data, FilterConfig.CheckFilter.threadLocal.get(), getCurrentRequest().getRequestURI());
                Thread.sleep(1500);
                return "received data : " + data;
            });
        }

        /**
         * 서블릿 request를 얻음
         *
         * @return
         */
        public static HttpServletRequest getCurrentRequest() {
            try {
                return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
