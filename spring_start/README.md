# Springboot Start 파헤치기

### springboot project 를 처음 만들었을때 기본으로 만들어준 코드가 어떻게 서버로써 동작하는지 알아봅니다.
```java
@SpringBootApplication
public class SpringStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringStartApplication.class, args);
    }

}
```