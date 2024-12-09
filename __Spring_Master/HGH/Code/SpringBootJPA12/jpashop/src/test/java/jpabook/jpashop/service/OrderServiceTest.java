package jpabook.jpashop.service;


import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문(){
        //given
        Member member = createMember();

        Item book = createBook();

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), 2);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        Assert.assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        Assert.assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        Assert.assertEquals("주문 가격은 가격 * 수량이다", 20000, getOrder.getTotalPrice());
        Assert.assertEquals("주문 수량만큼 재고가 줄어야 한다", 8,book.getStockQuantity());

    }

    private Item createBook() {
        Item book = new Book();
        book.setName("시골 JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가" , "123-123"));
        em.persist(member);
        return member;
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과(){

        //given
        Member member = createMember();
        Item book = createBook();

        int orderCount =11;
        //when
        orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Assert.fail("재고 수량 부족 예외가 발생하여야 한다.");
    }

    @Test
    public void 주문취소(){
        //given
        Member member = createMember();
        Item book = createBook();

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // when

        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        Assert.assertEquals("주문 취소시 상태는 CANCEL이다.", OrderStatus.CANCEL, getOrder.getStatus());
        Assert.assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.", 10, book.getStockQuantity());


    }


}