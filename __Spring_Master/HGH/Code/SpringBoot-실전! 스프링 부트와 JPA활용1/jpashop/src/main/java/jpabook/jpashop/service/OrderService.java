package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     *  주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count){

        // 이렇게 엔티티에 기능이 다 있고, 기능을 활용만 하는 패턴을 도메인 모델 패턴이라고 한다.
        // 반대로 서비스 계층에서 대부분의 비즈니스 로직을 처리하는 것을 스크립트 패턴이라고한다.
        // 이 곳에서는 도메인 - 모델 패턴으로 만들었다.
        // 무엇이 좋고 나쁜 것이아니고, 문맥에 따라서 잘 맞는 것을 사용하면 된다.

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        // OrderItem orderItem = new OrderItem(); 
        // 객체 생성의 일관성을 유지하기 위해서 생성자를 통한 생성을 막고 ,한가지 방법으로 생성을 유도한다
        // protected 접근 제한자를 이용한다(JPA에서는 protected를 사용하지 말라는 의미로 사용한다.)
        // 롬복 @NoArgsConstructor(access = AccessLevel.PROTECTED) 이용하는것이 좋다
        
        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId){
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        order.cancel();
    }

    // 검색
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByCriteria(orderSearch);
    }

}
