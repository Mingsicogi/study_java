package minssogi.study.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }


    @Controller
    public static class Welcome {

        RestTemplate restTemplate = new RestTemplate();

        @GetMapping("/")
        public String welcome(Model model) {
            ResponseEntity<String> forEntity = restTemplate.getForEntity("", String.class);
            model.addAttribute("innerHtml", forEntity.getBody());
            return "welcome";
        }
    }
}
