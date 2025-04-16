package bitcopark.library.service.Member;

import bitcopark.library.entity.member.Address;
import bitcopark.library.entity.member.Member;
import bitcopark.library.entity.member.MemberGender;
import bitcopark.library.exception.EmailDuplicateException;
import bitcopark.library.repository.Member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
//@Commit
@Transactional
class MemberServiceIntegrationTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Test
    public void 회원가입(){

        //given
        String email = "test@email.com";
        String name = "member1";
        String phoneNumber = "01012345678";
        MemberGender gender = MemberGender.MALE;
        int birth = 911111;
        String zipcode = "12345";
        String detailed = "D동";
        Address address = new Address(zipcode, detailed);
        Member member = memberService.joinMember(email, name, phoneNumber, gender, birth, address);

        // when
        Member findMember = memberRepository.findByEmail(email);

        //then
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void 관리자가입(){

        //given
        String email = "test@email.com";
        String name = "member1";
        String phoneNumber = "01012345678";
        MemberGender gender = MemberGender.MALE;
        int birth = 911111;
        String zipcode = "12345";
        String detailed = "D동";
        Address address = new Address(zipcode, detailed);
        Member member = Member.createAdmin(email, name, phoneNumber, gender, birth, address);
        memberRepository.save(member);

        //when
        Member findMember = memberRepository.findByEmail("email");

        //then
        assertThat(member).isEqualTo(findMember);
    }

    @Test
    public void 임베디드타입_동등성비교(){
        Address adr1 = new Address("13131", "D동");
        Address adr2 = new Address("13131", "D동");
        assertThat(adr1).isEqualTo(adr2);
    }

    @Test
    public void 이메일중복_에러발생(){

        //given
        String email1 = "test@email.com";
        String name1 = "member1";
        String phoneNumber1 = "01012345678";
        MemberGender gender1 = MemberGender.MALE;
        int birth1 = 911111;
        String zipcode1 = "12345";
        String detailed1 = "D동";
        Address address1 = new Address(zipcode1, detailed1);
        memberService.joinMember(email1, name1, phoneNumber1, gender1, birth1, address1);

        //when then
        assertThrows(EmailDuplicateException.class, () -> {
            memberService.joinMember(email1, name1, phoneNumber1, gender1, birth1, address1);
        });
    }

}