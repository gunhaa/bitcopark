package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest(){

        // Close를 하기위한 ApplicationContext의 하위 클래스이다.
        // 하위 클래스로 갈수록 더 구체적인 기능이 들어있다.
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);

        ac.close();
        /* 왜 이런 결과가 나옴??
            생성자 호출, url : null
            connect : null
            call : null message : 초기화 연결 메시지
        Bean의 라이프 사이클 때문이다
        객체생성 -> 의존관계 주입 순으로 진행된다.
        */


    }

    @Configuration
    static class LifeCycleConfig{

//        @Bean(initMethod = "init", destroyMethod = "close")
        @Bean
        public NetworkClient networkClient(){
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }

    }
}
