package hello.core.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    MemberService memberService = new MemberServiceImpl();

    @Test
    void join(){

        //given ~가 주어졌을때
        Member member = new Member(1L , "memberA" , Grade.VIP);
        //when 이렇게 했을때
        memberService.join(member);
        Member findMember = memberService.findMember(1L);
        //then 이렇게 된다
        Assertions.assertThat(member).isEqualTo(findMember);

    }

}
