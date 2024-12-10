package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class) // Springboot와 Integration test를 위한 어노테이션
@SpringBootTest // SpringBoot을 띄운 상태로하려면 필수, 없다면 Autowired실패
@Transactional // Rollback 가능
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    //@Rollback(false) // @Rollback(false)어노테이션이 있어야 insert문을 볼 수 있다..
                    // Rollback을 할 예정이 있다면, JPA가 insertQuery 자체를 날리지 않는다.
    public void 회원가입(){
        // given ~가 주어졌을때
        Member member = new Member();
        member.setName("kim");

        // when ~가 실행되면
        // save를 한다고 insert문이 나가지 않는다.
        Long saveId = memberService.join(member);

        // then ~가 되야한다
                                    // 같은 Transaction 안에서 같은 Entity, PK 값이 같다면
                                    // 같은 영속성 컨텍스트 안에서 같은 값으로 관리된다.
        //em.flush(); // 영속성 컨텍스트에 있는걸 나가게 만든다(insert문이 나감)
        Assertions.assertEquals(member, memberRepository.findOne(saveId));

    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예약(){

        //given
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setName("kim1");
        member2.setName("kim1");

        //when
        memberService.join(member1);
/*      @Test(expected = 예외)를 이용해서 생략할 수 있다
        try{
            memberService.join(member2); // 예외가 발생해야한다.
        } catch (IllegalStateException e){
            return;
        }
*/
        memberService.join(member2); // 예외가 발생해야한다.
        //then
        Assertions.fail("예외가 발생해야 한다.");

        // test폴더에 resources 폴더를 추가한 후 , application.yml 을 추가하면
        // test에서의 설정을 따로 설정할 수 있다
        // 해당 강의에서는 test에는 yml 설정을 통해 h2데이터 베이스를 메모리로 로딩해서 사용한다.
        // yml 스프링 설정이 없다면, 기본적으로 h2 메모리를 이용해서 실행시킨다
    }

}