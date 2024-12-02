package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {

    public static void main(String[] args) {

//        AppConfig appConfig = new AppConfig();
//
//        MemberService memberService = appConfig.memberService();
//        OrderService orderService = appConfig.orderService();

        // ApplicationContext를 스프링 컨테이너라고 한다
        // 기존에는 개발자가 직접 객체를 생성하고 DI 했지만, 스프링을 사용하면 스프링 컨테이너를 이용한다.
        // 스프링 컨테이너는 @Configuration이 붙은 AppConfig를 설정 정보로 사용한다. @Bean이라고 적힌 메소드를 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록한다.
        // 이렇게 스프링 컨테이너에 등록된 객체를 스프링 빈이라고 한다.
        // 스프링 빈은 @Bena이 분은 메소드 명을 Bean이름으로 사용한다.
        // 스프링 빈 객체는 applicationContext.getBean()으로 찾는다.
        // 기존에는 개발자가 직접 자바코드로 찾았다면 이제는 스프링 컨테이너에 객체를 등록하고 스프링 컨테이너에서 빈을 찾도록 바뀌었다.

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService = applicationContext.getBean("memberService" , MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService" , OrderService.class);

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 20000);

        System.out.println("order = "+ order);
        System.out.println("order.calculrateprice = " + order.calculatePrice());

    }

}
