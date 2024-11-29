package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;


    @BeforeEach
    public void beforeEach(){
        // memberService입장에서는 자신이 생성하지 않은 객체를 외부에서 주입받는다
        // 이를 DI(Dependency Injection, 의존성 주입) 이라고 한다.
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {

        //given : 무언가가 주어졌을때
        Member member = new Member();
        member.setName("hello");
        // when : 이거를 실행했을때
        Long saveId = memberService.join(member);
        // then : 결과가 이게 나와야함
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    @Test
    public void 중복_회원_예외(){

        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        //when

        memberService.join(member1);
        /*
        try {
            memberService.join(member2);
            fail();
        }catch(IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원 입니다.");
        }*/

        // 더 편하게 쓰기 위한 문법
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원 입니다.");

        //then

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}