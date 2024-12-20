package bitcopark.library.service;

import bitcopark.library.entity.member.Address;
import bitcopark.library.entity.member.Member;
import bitcopark.library.entity.member.MemberGender;
import bitcopark.library.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 중복되지_않은_이메일(){

        // given
        String email = "test@email.com";

        // when
        boolean isDuplicatedEmail = memberRepository.existsByEmail(email);

        // then
        assertThat(isDuplicatedEmail).isFalse();
    }

    @Test
    public void 중복된_이메일(){

        //given
        String email = "test@email.com";
        String name = "member1";
        String phoneNumber = "01012345678";
        MemberGender gender = MemberGender.MALE;
        int birth = 911111;
        String zipcode = "12345";
        String detailed = "D동";
        Address address = new Address(zipcode, detailed);
        Member member = Member.createMember(email, name, phoneNumber, gender, birth, address);
        memberRepository.save(member);

        // when
        boolean isDuplicatedEmail = memberRepository.existsByEmail(email);

        // then
        assertThat(isDuplicatedEmail).isTrue();

    }

}