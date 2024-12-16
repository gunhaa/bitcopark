package study.data_jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
public class Team {

    @GeneratedValue
    @Id
    @Column(name="team_id")
    private Long id;
    private String name;

    // fk가 아닌곳에서 mappedBy가 필요하다.
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    public Team(String name){
        this.name = name;
    }

}
