package bitcopark.library.service;

import bitcopark.library.domain.member.Address;
import bitcopark.library.domain.member.Gender;
import bitcopark.library.domain.member.Member;
import bitcopark.library.domain.member.MemberRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class MemberServiceTest {

    @Autowired MemberService memberService;

    @Test
    void 회원가입() {
        Member member = Member.builder()
                .email("test1@gmail.com")
                .password("test1234")
                .name("test1234")
                .phoneNumber("01012345678")
                .gender(Gender.FEMALE)
                .dateOfBirth("01012345678")
                .address(new Address("도시", "거리", "11111"))
                .role(MemberRole.USER)
                .build();

        Long saveId = memberService.join(member);

        assertThat(saveId).isEqualTo(memberService.findById(saveId).getId());
    }

    @Test
    void 중복_회원_예외() {
        Member member1 = Member.builder()
                .email("test1@gmail.com")
                .password("test1234")
                .build();

        Member member2 = Member.builder()
                .email("test1@gmail.com")
                .password("test5678")
                .build();

        memberService.join(member1);

        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }
}