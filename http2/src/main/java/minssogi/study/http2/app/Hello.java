package minssogi.study.http2.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Hello {

    @GetMapping("/welcome/http2/serverPush")
    public String welcome() {
        return "welcome_http2_server_push";
    }
}
