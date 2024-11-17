# Bean을 조회해서 두 가지 Bean이 모두 필요한 경우

> 예를 들면, 고객이 주문을 했는데 경우에 따라서 할인 정책이 바뀌기 때문에 두가지 빈을 모두 필요로 할 수 있다.
```plaintext
Autoappconfig
bean : autoAppConfig Object : hello.core.AutoAppConfig$$SpringCGLIB$$0@253d9f73
bean : memberServiceImpl Object : hello.core.member.MemberServiceImpl@142269f2
bean : memoryMemberRepositry Object : hello.core.member.MemoryMemberRepositry@331acdad
bean : fixDiscountPolicy Object : hello.core.discount.FixDiscountPolicy@41d426b5
bean : rateDiscountPolicy Object : hello.core.discount.RateDiscountPolicy@8dbfffb
bean : orderServiceImpl Object : hello.core.order.OrderServiceImpl@f316aeb

AppConfig
bean : appConfig Object : hello.core.AppConfig$$SpringCGLIB$$0@545b995e
bean : memberRepository Object : hello.core.member.MemoryMemberRepositry@76a2ddf3
bean : discountPolicy Object : hello.core.discount.FixDiscountPolicy@524f3b3a
bean : memberService Object : hello.core.member.MemberServiceImpl@41e68d87
bean : orderService Object : hello.core.order.OrderServiceImpl@49ff7d8c
```
- static class여야만 스프링이 찾을 수 있는 이유는 무엇인가????

> static 키워드가 붙어야만 빈을 찾을 수 있는 이유는 스프링 컨테이너에서 관리하는 클래스와 정적 내부 클래스(static nested class)의 동작 방식 때문이다.
> 반면, **정적 내부 클래스(static nested class)** 는 외부 클래스의 인스턴스와 무관하게 독립적으로 사용될 수 있다. 이는 스프링 컨테이너가 정적 내부 클래스를 쉽게 관리할 수 있다는 뜻이다.
> static class가 아니라면 스프링이 Bean을 찾지 못한다

## 다형성을 이용해서 야무지게 할 수 있다/ 유연한 전략 패턴을 쉽게 구현할 수 있다.

```java
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
        // 간단하게 구현하는 전략패턴
        // 다형성을 최대한 활용하는 방법
        public int discount(Member member, int price, String discountCode){
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            return discountPolicy.discount(member, price);
        }
    }
```