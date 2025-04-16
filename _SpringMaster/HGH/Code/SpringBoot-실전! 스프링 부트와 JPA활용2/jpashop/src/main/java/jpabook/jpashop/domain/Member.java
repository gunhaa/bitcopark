package jpabook.jpashop.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String name;

    @Embedded
    private Address address;
                // 매핑 됬음
    @OneToMany(mappedBy = "member")
    // 양 방향연관관계라면 한곳은 ignore해줘야 한다.
    @JsonIgnore // json 요청시 없으면 내보내지 않는다.
    private List<Order> orders = new ArrayList<>();

}
