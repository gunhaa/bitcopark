package jpabook.jpashop.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {


    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;


    // Lazy로딩은 Order객체 생성시 , Member 객체가 바로 생성되지 않고 Member member = new ByteBuddyInterceptor(); 같이 프록시 임시 객체로 생성되고 필요할때 생성된다.(byteBuddy 라이브러리 사용)
                // ManyToOne의 기본동작은 Eager, 모두 찾아서 Lazy로 설정하게 되지 않으면 많은 애로사항이 일어난다. 수동으로 모두 바꿔야함
    @ManyToOne(fetch = FetchType.LAZY)
                // 매핑 주체
    @JoinColumn(name = "member_id")
    private Member member;

    @BatchSize(size = 5)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    // java8에서는 hibernate가 쓰면 알아서 지원해줌
    // 이름 변경 전략 - 카멜케이스는 자동으로 스프링 부트에서 order_date로 바뀌어서 DB에 저장된다.
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태[ORDER, CANCEL]

    // == 연관 관계 메서드 == 관계가 양방향일때 쓰면 좋다.

    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //== 생성 메서드 ==//
    // 생성자를 대신하기위해, static이 반드시 필요하다.
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    // ==비즈니스 로직==
    /**
     * 주문 취소
     */
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송된 상품은 취소가 불가능 합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    // == 조회 로직 ==//
    /**
     *  전체 주문 가격 조회
     */
    public int getTotalPrice(){
        /*
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        같은 코드가 리팩토링 된 것이다.
        */
        int totalPrice = orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
        return totalPrice;
    }


}
