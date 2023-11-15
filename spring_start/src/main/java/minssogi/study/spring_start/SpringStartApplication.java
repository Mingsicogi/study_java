package minssogi.study.spring_start;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

//@SpringBootApplication
public class SpringStartApplication {

    public static void main(String[] args) {

        TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();

        WebServer tomcatWebServer = tomcatServletWebServerFactory.getWebServer(new ServletContextInitializer() {
            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                servletContext.addServlet("HelloController", new HttpServlet() {

                    HelloController helloController = new HelloController();
                    @Override
                    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                        String msg = req.getParameter("msg");
                        String response = helloController.hello(msg);
                        resp.getWriter().write(response);
                        resp.setStatus(HttpStatus.OK.value());
                        resp.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE
                        );
                    }
                }).addMapping("/hello");
            }
        });

        tomcatWebServer.start();
    }

}
