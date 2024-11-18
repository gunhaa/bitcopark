package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonTest {


    @Test
    void singletonBeanFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);

        SingletonBean bean1 = ac.getBean(SingletonBean.class);
        SingletonBean bean2 = ac.getBean(SingletonBean.class);

        System.out.println("bean1 = " + bean1);
        System.out.println("bean2 = " + bean2);

        ac.close();
        /* close 호출하여도 테스트가 통과되는 이유는 뭘까?

        close()를 호출하면 스프링 컨테이너가 소멸하며, 내부적으로 관리하던 빈들도 소멸된다.
        하지만 이는 메모리에서 객체를 즉시 제거하는 것이 아니라, 컨테이너가 더 이상 객체를 관리하지 않는다는 의미다.
        즉, close() 호출 전 반환된 bean1, bean2는 여전히 JVM 힙 메모리에 존재하며, GC가 처리하기 전까지는 사용할 수 있다.

        */

        Assertions.assertThat(bean1).isSameAs(bean2);

    }




    @Scope("singleton")
    static class SingletonBean{

        @PostConstruct
        public void init(){
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void destroy(){
            System.out.println("SingletonBean.destroy");
        }
    }

}
