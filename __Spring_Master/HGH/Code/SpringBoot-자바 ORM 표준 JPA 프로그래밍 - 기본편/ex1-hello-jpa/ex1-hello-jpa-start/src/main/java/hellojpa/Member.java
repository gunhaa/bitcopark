package hellojpa;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Member {

/*
    @Id
    private Long id;

    // unique 제약조건 설정 가능
    @Column(unique = true, length = 15)
    private String name;
    private int age;
*/
    // PK매핑
    @Id
    private Long id;

    // DB에 name으로 쓰고 싶을 때
    @Column(name = "name")
    private String username;

    // 다른 타입을 쓸 수 도있다.
    // 가장 적절한 타입이 매칭된다.
    private Integer age;

    //DB에서 ENUM을 쓰기 위한 방법
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // 3가지 타입이 존재한다.
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    // VARCHAR를 넘는 큰 것을 넣을때
    @Lob
    private String description;

    // getter, setter....

    // JPA는 리플렉션을 통해 객체를 생성하기 때문에 필수적이다, 접근 제한자는 상관X
    public Member(){}
}
