# JPA
- JPA는 기존의 반복 코드는 물론이고, 기본적인 SQL도 JPA가 직접 만들어서 실행해준다.
- JPA를 사용하면, SQL과 데이터 중심의 설계에서 객체 중심의 설계로 패러다임을 전환을 할 수 있다.
- JPA를 사용하면 개발 생산성을 크게 높일 수 있다
- 스프링만큼 깊이가 있는 기술이다
- build.gradle에 dependecy추가 `implementation 'org.springframework.boot:spring-boot-starter-data-jpa'` JDBC는 주석처리, jpa가 jdbc를 포함한다.
- JPA는 인터페이스이고, 구현체로 hibernate가 제공된다.
- JPA는 ORM(Object Relational Mapping)기술이다. 
   - Mapping방법은 어노테이션을 이용한다.(@Entity)
- JPA를 사용하기 위해선 항상 @Transactional이 있어야한다 (@Service)

## JPQL

- 테이블 대상의 SQL이 아닌 객체가 대상인 SQL이다.
- ex) `select m from Member (as) m` 객체 자체를 검색함
- ex) `select m from Member m where m.name = :name` 객체 중 name 프로퍼티를 검색함

# 스프링 데이터 JPA

> JPA+Springboot를 더 편하게 쓸 수 있는 JPA 라이브러리(학습 전에 반드시 JPA를 학습해야한다.)

> 리포지토리에 구현 클래스 없이 인터페이스만으로 개발할 수 있다.

- `public interface SpringDataJpaMemberRepository extends JpaRepository` 로 구현한다.

- 인터페이스가 인터페이스를 받을때는 extends를 사용한다.

- 인터페이스는 다중 상속이 가능하다

-  실무에서는 JPA와 스프링 데이터 JPA를 기본으로 사용하고, 복잡한 동적 쿼리는 Querydsl이라는 편리하게 작성할 수 있다. 이 조합으로 해결하기 어려운 쿼리는 JPA가 제공하는 네이티브 쿼리를 사용하거나, 앞서 학습한 스프링 JdbcTemplate를 사용하면 된다.