package jpabook.jpashop.api;


import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.query.OrderFlatDto;
import jpabook.jpashop.repository.order.query.OrderItemQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryRepository;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    // 해당 api는 정상적으로 반환되긴 하지만 엔티티가 직접 노출되는 문제가 있다.
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o->o.getItem().getName());
        }

        return all;
    }


    // 컬렉션을 찾는거라 쿼리가 정말 많이 나간다.
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
        return result;
    }




    // 2와 3의 결과는 같지만, 3은 쿼리 한번에 모든 것을 반환한다.
    // **일대 다 는 페이징이 '불가능' 하다.**
    // 가능은 하지만 메모리내에서 진행해서 out of memory의 위험성이 있다.
    // 일대 다 조인을 하는 순간 Order의 기준 자체가 틀어져버린다.
    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithItem();
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
        return result;
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "value", defaultValue = "100") int limit
    ) {
        // member와 delivery는 ToOne관계라서 fetch join을 통해 가져와도 페이징 처리가 가능하다.
        // 해당 방법은 orderitem에서 N+1 문제를 유발한다.
        // application.yml의 설정 default_batch_fetch_size: 100 를 통해서 어느정도 최적화 할수 있다. (해당 옵션은 미리 객체를 가지고 오는 옵션이다.)
        // in절에 fetch size만큼 null로 들어가는 것은 버전업에 따라 전략이 바뀌어서 그렇고, 성능에는 차이가 없다.
        // 엔티티에 @BatchSize(size = n) 어노테이션을 사용해서 디테일한 설정을 할 수 있다.
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
        return result;
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4(){

        // new연산자로 컬렉션을 가져올 수는 없다.
        // 최적화 시키긴 했지만 결국 N+1이다.

        List<OrderQueryDto> result = orderQueryRepository.findOrderQueryDtos();
        return result;
    }


    // 쿼리 2번으로 최적화를 할 수 있다.
    // toOne 관계를 먼저 조회하고, 여기서 찾아낸 것으로 toMany관계를 한꺼번에 조회
    // Map을 사용해서 성능 향상
    // 코드가 길어짐과 반비례해서 성능이 좋아지는 트레이드 오프가 있다.
    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5(){
        return orderQueryRepository.findAllbyDto_optimization();
    }

    @GetMapping("/api/v6/orders")
    public List<OrderQueryDto> ordersV6(){
        //한방 쿼리가 나간다.
        // 대신 4개의 결과가 나온다(조인 특성상 어쩔 수 없다.)
        // 페이징이 불가능하다.
        List<OrderFlatDto> flats = orderQueryRepository.findAllbyDto_flat();
        
        // DTO에 넣기 위한 코드
        return flats.stream()
                .collect(groupingBy(o -> new OrderQueryDto(o.getOrderId(),
                                o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                        mapping(o -> new OrderItemQueryDto(o.getOrderId(),
                                o.getItemName(), o.getOrderPrice(), o.getCount()), toList())
                )).entrySet().stream()
                .map(e -> new OrderQueryDto(e.getKey().getOrderId(),
                        e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(),
                        e.getKey().getAddress(), e.getValue())).collect(toList());
    }

    @Data
    static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        // OrderItem도 DTO로 바꿔주어야 한다.. 외부로 entity를 노출시키지 말 것
//        private List<OrderItem> orderItems;
        private List<OrderItemDto> orderItems;

        public OrderDto(Order order){
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
//            order.getOrderItems().stream().forEach(o->o.getItem().getName());
//            this.orderItems = order.getOrderItems();
            this.orderItems = order.getOrderItems().stream()
                    .map(orderItem -> new OrderItemDto(orderItem))
                    .collect(toList());
        }
    }

    @Getter
    static class OrderItemDto{
        // 나는 이름만 필요함
        private String itemName; // 상품명
        private int orderPrice; // 주문 가격
        private int count; // 주문 수량

        public OrderItemDto(OrderItem orderItem){
            this.itemName = orderItem.getItem().getName();
            this.orderPrice = orderItem.getOrderPrice();
            this.count = orderItem.getCount();
        }

    }

}
