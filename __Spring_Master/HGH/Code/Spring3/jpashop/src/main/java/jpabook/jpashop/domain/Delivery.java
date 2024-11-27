package jpabook.jpashop.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch=FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;
    // 기본은 Ordinal -> 1,2,3 숫자로 나옴, 중간에 딴거 새로 들어가면 대참사
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // READY , COMP

}
