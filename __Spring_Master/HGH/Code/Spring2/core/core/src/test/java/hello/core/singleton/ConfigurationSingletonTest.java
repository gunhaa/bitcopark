package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepositry;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    @DisplayName("싱글톤 객체안의 객체는 싱글톤인가? 맞으면 통과해야한다.")
    void configurationSingletonTest(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository" , MemberRepository.class);


        System.out.println("memberRepository = " + memberRepository);
        System.out.println("orderService -> memberRepo = " + orderService.getMemberRepository());
        System.out.println("memberService -> memberRepo = " + memberService.getMemberRepository());

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }

    @Test
    void configurationDeep(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("class : " + bean.getClass());
        // 예상 결과 = class : class hello.core.AppConfig
        // 결과 = class : class hello.core.AppConfig$$SpringCGLIB$$0
        // why? 예상과는 다르게 클래스에 xxxCGLIB가 붙으면서 복잡해진 것을 볼 수 있다. 이것은 내가 만든 클래
        //스가 아니라 스프링이 CGLIB라는 바이트코드 조작 라이브러리를 사용해서 AppConfig 클래스를 상속받은 임의의 다
        //른 클래스를 만들고, 그 다른 클래스를 스프링 빈으로 등록한 것이다
    }

}
