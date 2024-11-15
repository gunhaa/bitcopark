package hello.core.member;

public class MemberServiceImpl implements MemberService{

    //private final MemberRepository memberRepository = new MemberMemoryRepository;

    // 해당 코드를 아래처럼 변경하면 이제 추상화에만 의존하며, DIP를 지킨다.
    // 해당 방식을 생성자 주입이라고 한다.

    private final MemberRepository memberRepository;

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
}
