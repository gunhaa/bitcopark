# DI(의존관계 주입)

1. 생성자 주입
2. 수정자 주입(Setter)
3. 필드 주입
4. 일반 메서드 주입

## 생성자 주입

- 생성자 호출 시점에 1번만 호출되는 것이 보장된다.

- 불변/필수 의존관계에 사용된다

- @Autowired를 통해서 진행되고, 스프링 컨테이너가 해당 어노테이션을 발견하면 스프링 컨테이너에서 빈을 조회해서 주입 시켜준다.

- 좋은 개발은 제약이 있어서, 불변성이 보장되는 것이다.

- @Autowired 어노테이션이 없어도, 생성자가 하나라면 자동으로 주입한다. (파라미터의 개수는 상관X)


## 수정자 주입

- `setMemberRepostiory` 같은 메소드를 카멜케이스로 만들어서 주입한다
- @Autowired 어노테이션이 필요하다

> @Autowred는 주입할 대상이 없으면 오류가 발생한다. 주입할 대상이 없어도 동작하게 하려면 (required = false)옵션을 사용하면 된다.

## 필드 주입

- 편하다
- 하지만 추천되지 않는다
- Unit테스트에서 문제가 생긴다. setter를 연 후 new로 주입해야하기때문에 문제가 생길 가능성이 높아서 사용하면 안되는 안티 패턴이다.
- 테스트에서 Spring Container가 필요하다
- 생각보다 순수한 자바코드로 테스트 할 일이 많아서 지양해야한다.
- 순환 참조 문제가 생긴다
- 일반적인 테스트코드에선 써도 됨

## 일반 메서드 주입

- 사실 수정자 주입과 비슷하다(수정자랑 이름만 다름)
- 한번에 여러 필드를 주입 받을 수 있다

## Autowired의 옵션처리

> 스프링 빈이 없어도 동작해야할 때가 있다

- Autowired만 사용하면 required의 기본 옵션이 true로 되어 있어서 자동 주입 대상이 없으면 오류가 발생한다

### required = false

> 없다면 호출 자체가 안된다

### @Nullable

> 없어도 호출이 된다

### Optional<>

> Optional을 넣어 호출된다


## 생성자 주입을 선택해야한다! 

> 최근 DI 프레임 워크 대부분이 생성자 주입을 권장한다.

1. 의존관계 주입은 종료시점까지 의존관계를 변경할 필요가 없다.

2. 수정자 주입은 setXXX를 public으로 열어야해서 문제가 발생한다
> 누군가 실수할 수도 있어서 좋은 설계가 아니다

3. 1번만 호출되므로 호출될일이 없어서 불변하게 설계할 수 있다

4. 테스트할때 유용하다 임시 객체를 만들어서 넣을 수 있다.

5. final 키워드를 넣을 수 있다(값을 반드시 할당해야해서 개발자의 실수를 막아줌)