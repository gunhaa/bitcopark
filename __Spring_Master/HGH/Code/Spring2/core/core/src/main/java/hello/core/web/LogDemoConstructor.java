package hello.core.web;

import hello.core.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoConstructor {

    private final LogDemoService logDemoService;

    // 해당 주입은 스프링 컨테이너를 띄우면서 일어나는데, MyLogger의 스코프는 Request라 요청이 와야만 만들어진다
    // 즉 , 만들 수 없다.
    // 그래서 Provider를 통해서 해결해야한다.
    //private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request){
        String requestURL = request.getRequestURL().toString();
        myLogger.setRequestURL(requestURL);
        myLogger.log("Controller test");
        logDemoService.logic("testId");
        return "OK";
    }

}
