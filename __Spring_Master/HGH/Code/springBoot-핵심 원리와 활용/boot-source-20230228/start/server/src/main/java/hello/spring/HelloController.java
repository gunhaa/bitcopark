package hello.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // dispatcher servlet이 spring에 반응하기 때문에 /spring/hello-spring으로 매핑된다.
    @GetMapping("/hello-spring")
    public String hello(){
        System.out.println("HelloController.hello");
        return "hello spring!";
    }
}
