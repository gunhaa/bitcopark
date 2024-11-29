package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepositry;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // @Bean memberService -> new MemoryMemberRepository();
    // @Bean orderService -> new MemoryMemberRepository();
    // MemoryMemberRepository는 이렇게 두번 호출되었는데 싱글톤이 깨지는 것 아닌가?

    // 호출 예상
    // 만약 @Configuration어노테이션을 제거하면 예상과 같이 나타난다.
//    call AppConfig.memberRepository

//    call AppConfig.memberService
//    call AppConfig.memberRepository
//    call AppConfig.orderService
//    call AppConfig.memberRepository

    // 실제 호출
//    call AppConfig.memberRepository
//    call AppConfig.memberService
//    call AppConfig.orderService

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepositry();
    }

    @Bean
    public DiscountPolicy discountPolicy(){
        return new FixDiscountPolicy();
//        return new RateDiscountPolicy();
    }


    // 의존 관계를 주입한다.(DI)
    @Bean
    public MemberService memberService(){
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService(){
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }
}
