package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    // ViewResolver가 view를 반환한다
    // model 객체를 통해 view에 data를 전달한다(DispatcherServlet)
    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data" , "gdgdgdgd!!!");
        // .html이 자동으로 붙는다
        return "hello";
    }
}
