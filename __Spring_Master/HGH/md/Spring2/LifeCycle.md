# 스프링 빈 생명주기

## 스프링 빈의 라이프 사이클

> 객체 생성 -> 의존관계 주입

> **초기화와 소멸전에 콜백 메서드를 제공한다.

## 스프링 빈의 이벤트 라이프 사이클

> 스프링 컨테이너 생성 -> 스프링 빈 생성 -> 의존 관계 주입 -> 초기화 콜백 -> 사용 -> 소멸전 콜백 -> 스프링 종료

## 객체의 생성과 초기화를 분리하자 (하나의 책임을 하나의 메소드에)

- 생성자는 필수정보를 받고 메모리를 할당해서 객체를 생성한다.
- 초기화는 생성된 값을 이용해서 외부 커넥션을 연결하는 등 무거운 역할을 수행한다.
- 무거운 행동은 필요할 때 까지 미룰수 있는 것이 장점이다.

### 초기화와 생성자의 차이점

| **구분**      | **생성자**                                                    | **초기화 메서드**                                |
|---------------|--------------------------------------------------------------|-------------------------------------------------|
| **타이밍**    | 객체가 생성될 때 자동 실행됨                                    | 객체가 생성된 후에 명시적으로 호출해야 함          |
| **목적**      | 객체 생성과 필수적인 값 설정                                   | 외부 리소스 연결, 추가적인 설정, 무거운 작업 수행 |
| **작업 범위** | 주로 객체 내부 상태 초기화 (필드 값 설정 등 간단한 작업)         | 외부 시스템과의 연결, 네트워크 통신, 파일 처리 등 |
| **주요 특징** | 간단한 작업 위주로 빠르게 실행                                 | 리소스 소비가 큰 작업을 포함할 수 있음            |
| **예시**      | 필드 값 설정                                                  | 데이터베이스 연결, 파일 읽기 준비, 네트워크 연결  |

---

```java
// 생성자
public class User {
    private String username;

    public User(String username) {
        // 생성자: 필수적인 값 설정
        this.username = username;
    }
}
// 초기화
public class DatabaseConnection {
    private Connection connection;

    public DatabaseConnection() {
        // 생성자: 기본 객체 생성
    }

    public void init() throws SQLException {
        // 초기화: 외부 리소스 연결
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "user", "password");
    }
}
```

## 인터페이스를 이용한 AOP

> implements InitializingBean , DisposableBean 을 이용한다.

- 인터페이스를 implements해서 초기화 후, 종료사이클 전에 콜백을 받을 수 있다.
- 단점 : 스프링 인터페이스에 의존한다, 메서드 이름 변경 가능, 외부 라이브러리 적용 불가
- 지금은 거의 사용하지 않음(초창기 방법)

## 빈 등록 초기화/소멸 메서드 AOP

> 설정 정보에 `@Bean(initMethod = "init", destroyMethod = "close")` 처럼 초기화, 소멸 메서드를 지정 할 수 있다.

- 장점 : 메소드 이름 설정 가능 / 스프링에 의존하지 않는다. / 코드가 아니라 설정 정보를 사용하기 때문에 외부 라이브러리를 사용할 수 있다.

### destroyMethod 
> 외부 라이브러리의 경우 close / shutdown이라는 종료 메서드를 사용한다.

- 기본값이 (inferred)로 등록 되어있다(추론)

- 이 기능은 자동으로 close / shutdown을 호출 해준다.

- 따라서 등록하면 종료 메소드는 안적어줘도 된다.

- 사용하기 싫다면 "" 빈칸을 적으면 된다.(종료 안할때)


## @PostConstruct / @PreDestroy

> 스프링에서 권장하는 방식이다

> 이 것을 사용하면됨!!

- java의 기술이다. spring이외의 다른 컨테이너에서도 사용 가능하다

- 단점 -> 외부 라이브러리에는 적용시키지 못한다.

- 외부 라이브러리의 경우는 @Bean의 initMethod , destoryMethod를 사용하면 된다.

## 외부 라이브러리는 왜 @Bean을 사용해야함?

```java
// Bean의 외부 라이브러리 사용 방식
@Configuration
public class AppConfig {

    @Bean(initMethod = "initialize")
    public ExternalLibraryService externalLibraryService() {
        return new ExternalLibraryService(); // 빈 생성
    }
}
```

### 라이브러리의 init메소드를 어노테이션이 붙은 메소드 안에 넣으면 되는거아님?

> 라이브러리의 초기화 메서드를 어노테이션이 붙은 메서드(@PostConstruct) 안에 호출하는 방법도 가능하다. 
> 하지만 이 접근 방식에는 제약조건과 고려할게 많다.

```java
//예시코드
@Component
public class MyService {
    private final ExternalLibraryService externalLibraryService;

    public MyService(ExternalLibraryService externalLibraryService) {
        this.externalLibraryService = externalLibraryService;
    }

    @PostConstruct
    public void init() {
        // 외부 라이브러리 초기화 메서드 호출
        externalLibraryService.init();
    }
}
```

- 빈의 생성 순서 문제

@PostConstruct는 해당 빈이 생성된 후 호출된다.
따라서 외부 라이브러리의 인스턴스가 스프링 컨테이너에 의해 관리되는 빈이어야만 가능하다.
만약 외부 라이브러리 객체가 스프링 컨테이너에서 관리되지 않는다면, 직접 초기화 메서드를 호출하는 방식이 동작하지 않을 수 있다.

- 라이브러리 객체 재사용

    - 외부 라이브러리 객체를 다른 빈에서도 사용할 가능성이 있다면, 모든 곳에서 별도로 초기화 메서드를 호출해야 한다. 이는 코드 중복을 초래하거나 유지보수를 어렵게 만들 수 있다.
    - 초기화 작업은 해당 객체에만 적용된다. 그래서 외부 라이브러리 객체를 다른 빈에서도 재사용하려는 경우, 초기화 메서드를 여러 번 호출해야 할 수 있다는 문제점이 생긴다.

- 라이브러리 코드의 변경 가능성

외부 라이브러리의 초기화 메서드가 내부적으로 복잡하거나 추가적인 설정이 필요한 경우, 단순히 호출만으로 원하는 결과를 얻지 못할 수도 있다. 초기화 작업은 라이브러리 자체에서 정의된 대로 initMethod를 사용하는 것이 더 안전할 수 있다.