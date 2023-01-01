package minssogi.study.design_pattern.template_method;

import lombok.RequiredArgsConstructor;
import minssogi.study.design_pattern.template_method.bad.NaverLoginService;
import minssogi.study.design_pattern.template_method.bad.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final NaverLoginService naverLoginService;

    /**
     * http://localhost:8087/design_pattern/template_method/bad?loginType=NAVER&id=minssogi&password=123
     *
     * @param loginType
     * @param id
     * @param password
     * @return
     */
    @GetMapping("/design_pattern/template_method/bad")
    public ResponseEntity<String> loginRequest(String loginType, String id, String password) {
        try {
            if ("NAVER".equals(loginType)) {
                User loginUser = naverLoginService.login(id, password);
                return ResponseEntity.ok(loginUser.getName() + ", Welcome!");
            } else {
                return ResponseEntity.badRequest().body("Not supports '" + loginType + "'");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
