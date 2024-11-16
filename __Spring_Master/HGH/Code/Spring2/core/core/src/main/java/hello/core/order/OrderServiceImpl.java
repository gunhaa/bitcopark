package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

    //private final MemberRepository memberRepository = new MemoryMemberRepositry();
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    // 이렇게 해야 인터페이스에만 의존하게 된다.
    // 하지만 이렇게 작성하면 nullpointerException이 발생한다. 객체가 없기 때문에
    // 해결하기 위해선 누군가 객체를 만든 다음에 넣어 주어야한다.

    // final무조건 할당이 되어야한다.
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    // OrderServiceImpl은 이제 어떤 repository와 policy가 들어오는지 모르는, 대본만 보고 공연을 하는 배우와 같아진다.
    @Autowired // 여러 의존 관계도 한번에 주입받을 수 있다.
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId,itemName, itemPrice, discountPrice);
    }

    // 싱글톤 테스트용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }

}
