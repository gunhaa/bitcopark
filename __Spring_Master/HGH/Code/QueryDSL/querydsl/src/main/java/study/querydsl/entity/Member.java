package study.querydsl.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
// JPA는 기본생성자가 반드시 필요하다
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 편의성을 위해서 만듬 Team이 들어가면 안된다. stackoverflow 오류가 발생 할 수 있다.
@ToString(of = {"id", "username", "age"})
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    // JoinColumn은 연관관계의 주인이다. 해당 값을 이곳에서 업데이트 할 수 있다
    // 연관관계 업데이트 메서드 changeTeam()
    @JoinColumn(name = "team_id")
    // xxxToOne 관계에서는 항상 LAZY로 걸어놔야한다.
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    public Member(String username){
        this(username , 0, null);
    }

    public Member(String username, int age){
        this(username, age , null);
    }

    public Member(String username, int age, Team team){
        this.username = username;
        this.age = age;
        if(team != null){
            changeTeam(team);
        }
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

}
