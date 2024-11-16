package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{

    //private final MemberRepository memberRepository = new MemberMemoryRepository;

    // 해당 코드를 아래처럼 변경하면 이제 추상화에만 의존하며, DIP를 지킨다.
    // 해당 방식을 생성자 주입이라고 한다.

    private final MemberRepository memberRepository;

    // Component로 Bean으로 만들고, Autowired를 통해 자동 의존 관계 주입
    @Autowired // ac.getBean(MemberRepository.class) 와 같은 기능을 수행한다.
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 싱글톤 테스트용
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }

}
