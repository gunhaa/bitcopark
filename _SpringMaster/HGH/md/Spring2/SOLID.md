# SOLID
1. SRP 단일 책임 원칙 (Single responsibility principle)
2. OCP 개방 - 폐쇄 원칙(Open/Closed principle)
3. LSP 리스코프 치환 원칙(Liskov substitution principle)
4. ISP 인터페이스 분리 원칙(Interface segregation principle)
5. DIP 의존 관계 역전 원칙(Dependency inversion principle)

## 1. SRP 단일 책임 원칙 (Single responsibility principle)

> 하나의 클래스는 하나의 책임만 가져야 한다

- 책임이란?
    - 하나의 책임이라는 것은 모호하다
    - 중요한 기준은 변경이다. 변경이 있을때 파급효과가 적으면 단일 책임 원칙을 잘 따른것

- ex) UI 변경, 객체의 생성과 사용을 분리

## 2. OCP 개방 - 폐쇄 원칙(Open/Closed principle)

> 가장 중요한 원칙

> 소프트웨어 요소는 확장에는 열려있으나 변경에는 닫혀있어야한다.

> 다형성 활용

### 문제점
- `private MemberRepository memberRepository = new MemoryMemberRepository();` 기존 코드
- `private MemberRepository memberRepository = new JdbcMemberRepository();` 변경 코드
- 구현 객체를 변경하려면 클라이언트 코드를 변경해야한다.
- 분명 다형성을 사용했지만 OCP 원칙을 지킬 수 없다

#### 해결방법???

- 객체를 생성하고, 연관관계를 맺어주는 별도의 조립, 설정자가 필요하다(Spring 컨테이너가 문제를 해결한다.)

## 3. LSP 리스코프 치환 원칙(Liskov substitution principle)

> 자동차 인터페이스가 있을때 엑셀의 기능은 앞으로만 가야한다.

> 엑셀을 뒤로가게 구현한다면 리스코프 치환 원칙을 위반한 것이다.

> 즉, 인터페이스의 규약을 지켜서 객체를 만들어야한다.

## 4. ISP 인터페이스 분리 원칙(Interface segregation principle)

> 자동차 인터페이스 -> 운전 인터페이스/정비 인터페이스로 분리

> 사용자 클라이언트 -> 운전자 클라이언트/ 정비사 클라이언트로 분리

> 분리하면 정비 인터페이스가 변해도 운전자 클라이언트에 영향을 주지 않는다.

> 인터페이스가 명확해지고 대체 가능성이 높아진다.

## 5. DIP 의존 관계 역전 원칙(Dependency inversion principle)

> OCP다음으로 중요한 원칙이다.

> 구현 클래스에 의존하지말고, 인터페이스에 의존해야한다.

> 역할에 의존해야한다

> 즉, 운전자(클라이언트)는 자동차(인터페이스)만을 바라봐야하고 세부 내용은 몰라도 된다.

> 의존하지 말아야한다. (저 코드를 알고있다는 뜻)

- `private MemberRepository m= new MemoryMemberRepository();` => DIP 위반

## Recap

- 객체 지향의 핵심은 다형성이다.
- 하지만 다형성만으론 쉽게 부품을 갈아 끼울수 없고, 구현 객체를 변경할 때 클라이언트 코드도 함께 변경된다.
- 다형성 만으로는 OCP,DIP를 지킬 수 없다.
- 뭔가가 더 필요하다.