package minssogi.study.spring_start;

//@RestController
//@RequestMapping
public class HelloController {

//        @GetMapping("/hello")
        public String hello(String msg) {
            return "Hello World [msg : " + msg + "]";
        }
}
