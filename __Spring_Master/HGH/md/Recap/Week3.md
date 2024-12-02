# 리팩토링

> 실제로 서비스를 개발해 본 뒤, 한번 더 읽는 것이 좋을 것 같다

## 1. 코드의 품질은 변경 가능성과 유지보수성을 통해 측정된다
- 리팩터링은 코드의 기능을 변경하지 않으면서, 내부 구조를 개선하는 과정이다.
- 목표는 가독성, 확장성, 유지보수성을 높이는 것이다.
- 좋은 코드는 변화에 쉽게 적응할 수 있는 코드다.
- 추가 기능이 필요할 때 쉽게 추가할 수 있도록 인간이 알아볼 수 있는 구조로 만들어야 한다.

## 2. 작은 단계로 안전하게 변경하라

- 리팩터링은 작은 단계로 진행되어야 한다. 이렇게 하면 변경 도중 문제가 발생했을 때 쉽게 되돌릴 수 있다.
- Test로 품질을 유지하고, 리팩토링을 할 때 Test를 활용하여 작은 단위를 테스트해야한다.
- 자동화된 테스트는 리팩터링의 필수 요소다.

## 3. 코드 냄새(Code Smell)를 감지하라
- "코드 냄새"란 코드에 문제가 있다는 신호다. 직접적인 버그는 아니지만, 장기적으로 유지보수를 어렵게 만든다.
    - 중복 코드
    - 긴 메서드
    - 과도한 클래스
    - 의존성이 높은 모듈

## 4. 기술 부채를 줄여라

- 리팩터링은 코드 품질을 개선해 기술 부채(Technical Debt)를 갚는 과정이다.
    - 기술 부채는 소프트웨어 개발 과정에서 빠른 개발을 위해 품질을 희생한 결과로 남는 문제를 말한다.
    - 빠른 출시를 위해 설계를 간소화하거나 중복된 코드를 작성하면 단기적으로는 작업 속도가 빨라지지만, 이후 코드 변경이나 확장이 어려워지는 비용이 발생한다.

- 단기적으로는 추가 작업처럼 보이지만, 장기적으로는 개발 속도를 유지하고 코드 변경 비용을 줄여준다.

## 5. 리팩터링은 지속적인 과정이다

- 리팩터링은 한 번의 이벤트가 아니라 지속적인 과정이다.
- 새로운 요구사항을 반영할 때, 혹은 버그를 수정할 때마다 코드를 개선하는 기회로 삼아야 한다.
- 리팩터링을 위한 시간을 별도로 마련하기보다는, 기존 작업 흐름에 자연스럽게 포함시키는 것이 중요하다.
- 리팩토링을 해서 코드에 문제가 생긴다면, 그것은 리팩토링이아니라 리스트럭처링이다.
    - 리스트럭처링은 코드의 구조를 변경하면서, 외부 동작도 바뀌는 작업이다.

## 6. 코드는 단순하고 명확하며 의도가 드러나야 한다.

- 좋은 설계는 단순하고 명확하며, 의도가 드러나는 코드를 작성하는 것이다.

## 7. 리팩터링은 팀 문화다
- 리팩터링은 개인의 작업이 아니라 팀 전체의 문화로 자리 잡아야 한다.
- 코드 리뷰와 협업을 통해 리팩터링을 지속적으로 실행해야 한다.
- 팀원 모두가 코드 품질 개선에 책임을 가져야 한다.

## RECAP
> 리팩터링은 코드를 더 쉽게 이해하고, 수정하고, 확장할 수 있도록 개선하는 것이다. 이는 코드의 동작을 유지하면서 내부 설계를 개선하여 개발의 생산성을 높인다.

# Reflection & Proxy in JPA

> 구체적인 클래스 타입을 알지 못해도 그 클래스의 메소드, 타입, 변수들에 접근할 수 있도록 해주는 자바 API

- 런타임에 지금 실행되고 있는 클래스를 가져와서 실행해야하는 경우

- 동적으로 객체를 생성하고 메서드를 호출하는 방법

- 자바의 리플렉션은 클래스, 인터페이스, 메소드들을 찾을 수 있고, 객체를 생성하거나 변수를 변경하거나 메소드를 호출할 수 있다.

- JPA프록시 객체는 리플렉션을 통해서 만들어진다.

## 예제

### Entity

```java
public class Entity implements ParentInterface{

    private Long id;
    private String name;

    public Entity() {
    }

    // ...
    // getter , setter 생략

    public void print(){
        System.out.println("Entity print 출력 : id는 " + this.id + " 이름은 : " + this.name);
    }
}
```

### Reflection을 통한 Proxy 생성

```java
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ProxySample implements ParentInterface {

    private Class<?> originalClazz;
    private Object originalInstance;
    private Long id;
    private String name;


    public ProxySample(Class<?> proxyClazz, Long id , String name) {
        this.originalClazz = proxyClazz;
        this.id = id;
        this.name = name;
    }

    public void print(){
                    // 기본 생성자를 통해 인스턴스를 만들어낸다
        try {

            // type narrowing 필요
            originalInstance = originalClazz.getDeclaredConstructor().newInstance();
            // 메소드 호출 오류 처리 필요
            // 동적으로 필드 배열을 가져와서 처리해서 리팩토링 가능
            Field idField = originalClazz.getDeclaredField("id");
            Field nameField = originalClazz.getDeclaredField("name");

            // 이런식으로 동적 처리 가능
            Class<?> idType = idField.getType();
            Class<?> nameType = nameField.getType();

            // 여기도 동적으로 메소드배열 가져와서 리팩토링 가능
            Method setId = originalClazz.getMethod("setId", idType);
            Method setName = originalClazz.getMethod("setName", nameType);

            setId.invoke(originalInstance ,  this.id);
            setName.invoke(originalInstance,  this.name);

            // 여기도
            Method printMethod = originalClazz.getMethod("print");

            printMethod.invoke(originalInstance);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();
    }

```

### 실행
```java
package com.note.week3;

public class RuntimeClazz {

    public static void main(String[] args) {


        // 프록시 객체 생성
        // 생성시에는 최소한의 자원으로 객체를 만들고
        // 프록시의 print메소드 호출 시 원래 클래스의 필드값을 반환

        ProxySample proxy = new ProxySample(Entity.class, 1L, "ReflectionProxySample");
        proxy.print(); 
        // 결과 -> Entity print 출력 : id는 1 이름은 : ReflectionProxySample

    }
}

```
