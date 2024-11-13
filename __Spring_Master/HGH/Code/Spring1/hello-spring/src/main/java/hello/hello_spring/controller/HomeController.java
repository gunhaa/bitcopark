package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
                // 도메인
    @GetMapping("/")
    public String home(){
                // home.html 호출
        return "home";
    }
}
