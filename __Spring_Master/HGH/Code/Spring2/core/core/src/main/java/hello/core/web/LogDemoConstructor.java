package hello.core.web;

import hello.core.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
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
    // 타겟 객체에 proxyMode = ScopedProxyMode.TARGET_CLASS)을 추가해서 해결할 수도 있다.
    private final MyLogger myLogger;

    // provider를 사용한 방법
//    private final ObjectProvider<MyLogger> myLoggerProvider;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        String requestURL = request.getRequestURL().toString();
        // MyLogger 객체 생성시점
//        MyLogger myLogger = myLoggerProvider.getObject();

        // 같은 프록시 객체에서 얻는지 확인한다
        System.out.println("myLogger.getClass() = " + myLogger.getClass());
        System.out.println("Proxy instance: " + System.identityHashCode(myLogger));
        
        myLogger.setRequestURL(requestURL);
        myLogger.log("Controller test");
        // 지연 처리로 연속해서 요청시 로그가 달라지는 것을 알 수 있다.
//        Thread.sleep(1000);

        logDemoService.logic("testId");
        return "OK";
    }

}
