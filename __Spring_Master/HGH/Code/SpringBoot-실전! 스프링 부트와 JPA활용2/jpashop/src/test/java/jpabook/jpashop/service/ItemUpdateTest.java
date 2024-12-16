package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.text.html.parser.Entity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    public void updateTest() throws Exception {

        Book book = em.find(Book.class , 1L);

        // Transaction
        book.setName("asdasdasd");

        // 준영속entity > db에 한번 다녀온 객체 -> set을 해도 업데이트가 되지 않는다.
        // 그럼 어떻게 변경해야할까?
        // 1. 변경감지 기능 이용/ 2. merge이용
        // JPA는 커밋이 되면 관리하는 Entity 중 바뀐 것을 찾아서 반환시킨다.(1)
        // Merge를 이용하면 JPA가 엔티티를 관리하게 된다.(2) (Merge는 모든 것을 바꿔서 빠져있다면 기본값이 들어가서 위험한다.)

        // 결론 : 엔티티를 변경할때는 항상 변경 감지를 사용해야한다.
        
        // 변경감지 == dirty checking
        // Transaction commit

    }

}
