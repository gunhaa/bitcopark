# 비즈니스 요구 사항

> 예제 1은 순수한 자바코드로만 작성한다.

> 예제 1의 방법은 잘 작성된 것 같지만, 자세히 보면 인터페이스에 의존하고 있는 것이 아니라 구체적인 구현체에 의존한다.

## 어떻게 해결해야하는가?

### 예제 1의 문제점

- 애플리케이션을 하나의 공연이라고 생각할때, 각각의 인터페이스를 배역(배우)라고 가정하자
- 로미오와 줄리엣 공연을 하면 로미오 역할을 누가 할지 줄리엣 역할을 누가 할지는 배우들이 정하는게 아니다. 이전 코드는 마치 로미오 역할(인터페이스)을 하는 레오나르도 디카프리오(구현체, 배우)가 줄리엣 역할(인터페이스)을 하는 여자 주인공(구현체, 배우)을 직접 초빙하는 것과 같다. 디카프리오는 공연도 해야하고 동시에 여자 주인공도 공연에 직접 초빙해야하는 너무 많은 책임을 가지고 있다.

### 해결 방법

- 관심사를 분리해야 한다.
    - 배우는 본인의 역할인 배역을 수행하는 것에 집중해야한다.
    - 디카프리오는 어떤 여자 주인공이 오더라도 같은 공연을 해야한다.
    - 해결을 위해선 공연 구성, 담당 배우 섭외, 배우 지정하는 책임이 있는 공연기획자가 필요하다
    - 공연 기획자를 만들고, 배우와 공연기획자의 책임을 확실히 분리하자
=> AppConfig 클래스 생성(애플리케이션의 전체 동작 방식을 구성(config))하기 위해 생성한다 : 책임 ? 구현 객체를 생성/연결


```java 

    //private final MemberRepository memberRepository = new MemberMemoryRepository;

    // 해당 코드를 아래처럼 변경하면 이제 추상화에만 의존하며, DIP를 지킨다.
    // 해당 방식을 생성자 주입이라고 한다.
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

```

---

```java
    //private final MemberRepository memberRepository = new MemoryMemberRepositry();
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    // OrderServiceImpl은 이제 어떤 repository와 policy가 들어오는지 모르는, 대본만 보고 공연을 하는 배우와 같아진다.
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
```

---
```java
// 설정(AppConfig.class)
    private MemberRepository memberRepository() {
        return new MemoryMemberRepositry();
    }

    public DiscountPolicy discountPolicy(){
        return new FixDiscountPolicy();
//        return new RateDiscountPolicy();
    }

    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }

    public OrderService orderService(){
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

```


## Recap

> 즉 객체 지향적 설계를 위해서는 사용영역과 설정영역을 분리(OCP/DIP를 지키기 위한 방법)해야한다.

