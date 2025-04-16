package hello.core.common;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
// value는 빼도 된다.
//@Scope(value = "request")
@Scope(value = "request" , proxyMode = ScopedProxyMode.TARGET_CLASS)
// 가짜 프록시 클래스를 만들어두고 Request와 상관없이 가짜 프록시 클래스를 다른 빈에 미리 주입해 둘 수 있게 된다.
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message){
        System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message );
    }

    @PostConstruct
    public void init(){
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create:" + this);
    }

    @PreDestroy
    public void destroy(){
        System.out.println("[" + uuid + "] request scope bean close:" + this);
    }
}
