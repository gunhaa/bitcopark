package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    public void test1(){
        // 회원가입을 성공해야 한다.

        //given
        Member member = new Member();
        member.setName("hwang");
        Long memberId = memberService.join(member);
        //when
        Member findMember = memberService.findOne(member);
        //then
        System.out.println(member.getId());
        Assert.assertEquals(member , findMember);

    }
    // 회원 가입을 할 때 같은 이름이 있으면 예외가 발생해야 한다.
    @Test
    public void test2(){

        //given

        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");
        //when
        Long savedId1 = memberService.join(member1);
        try{
            Long savedId2 = memberService.join(member2);
        }catch (IllegalStateException e){
            return;
        }
        //then
        Assert.fail("예외가 발생해야한다 - '이미 존재하는 회원입니다.'");
    }


}