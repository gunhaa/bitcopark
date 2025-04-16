package study.querydsl.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
// JPA는 기본생성자가 반드시 필요하다
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 편의성을 위해서 만듬 member이 들어가면 안된다. stackoverflow 오류가 발생 할 수 있다.
@ToString(of = {"id", "name"})
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    private String name;
    // 연관관계의 거울
    // 외래키 값을 업데이트 하지 않는다
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }

}
