package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 * XtoOne의 성능 최적화(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
* */

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository OrderSimpleQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){

        // 엔티티를 그대로 노출하는 것은 좋은 방법이 아니다.
        // 엔티티가 바뀌면 API스펙 자체가 바뀐다.
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());

        for (Order order : all) {
            order.getMember().getName(); // Lazy 강제 초기화
            order.getDelivery().getAddress(); // Lazy 강제 초기화
        }

        return all;
    }


    // 1번 버전과 2번 버전은 모두 문제를 가지고 있다 - 쿼리가 너무 많이 나간다.
    // 현재 2개의 아이템을 가지고있고, 요청이 올때마다 5개의 쿼리가 나간다 - lazy loading에서 객체 생성시 필요한 것을 query로 찾아온다.
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        // ORDER 2개
        // N+1 -> 1+ 회원 N + 배송 N
        // 즉 5개의 쿼리가 나가게 된다.
        // 만약 같은 회원이 주문했다면, 그만큼 덜 나가게 된다.
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());

        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());

        return result;
    }

    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName(); // LAZY 초기화
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress(); // LAZY 초기화
        }
    }

    // v2와 v3의 동작은 같지만 쿼리가 다르다.
    // 굉장히 많이 사용하는 기법이다.
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        return orders.stream().map(o->new SimpleOrderDto(o)).collect(Collectors.toList());
    }

    // 꺼낼때 DTO로 꺼내는 최적화 방법
    // 어플리케이션 용량이 절약된다.. 하지만 그렇게 크지는 않다.
    // 3번까지 할만하다고 본다.
    // 4번은 이 것만을 위한 DTO를 만들어서 별로다.
    // DTO가 API에 의존해서 별로임
    // 3,4번의 트레이드 오프 결과이다.
    // 보통 이런정도의 성능차이는 없다 , 하지만 필드가 수십개라면 얘기가 달라지긴한다.
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4(){
        return OrderSimpleQueryRepository.findOrderDtos();
    }
    
}
