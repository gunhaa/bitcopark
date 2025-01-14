package study.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDto {

    private String username;
    private int age;


    // 해당 어노테이션을 이용해 Qfile로 DTO도 생성할 수 있다.
    // QueryDSL이 알 수 있게하는 어노테이션이다
    // 하지만 이렇게하면 MemberDto가 querydsl의존성을 가지게 되는 단점이있다.
    // ex) querydsl 라이브러리를 빼야할 경우
    // DTO가 많은 레이어에 흘러다닌다. querydsl 의존성이 있으니 아키텍쳐 적으로 위험 할 수있다.
    @QueryProjection
    public MemberDto(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
