package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

public class AllBeanTest {

    @Test
    void findAllBeans(){
        // 스프링 컨테이너에 Bean을 등록하는 코드
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);


//        for (String beanName : ac.getBeanDefinitionNames()) {
//            System.out.println(beanName);
//        }
//        내부 클래스의 이름 규칙은 allBeanTest.DiscountService가 되어서 찾을수가 없는 것이다.
        DiscountService discountService = ac.getBean("allBeanTest.DiscountService", DiscountService.class);

        Member member = new Member(1L, "userA" , Grade.VIP);

        int fixDiscountPolicy = discountService.discount(member, 10000, "fixDiscountPolicy");
        Assertions.assertThat(fixDiscountPolicy).isEqualTo(1000);

        int rateDiscountPolicy = discountService.discount(member, 20000, "rateDiscountPolicy");
        Assertions.assertThat(rateDiscountPolicy).isEqualTo(2000);
    }

    static class DiscountService {

        Map<String, DiscountPolicy> policyMap;
        List<DiscountPolicy> policies;

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        // 전략 패턴을 성공적으로 구현한 메소드
        public int discount(Member member, int price, String discountCode){
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            return discountPolicy.discount(member, price);
        }


    }

}
