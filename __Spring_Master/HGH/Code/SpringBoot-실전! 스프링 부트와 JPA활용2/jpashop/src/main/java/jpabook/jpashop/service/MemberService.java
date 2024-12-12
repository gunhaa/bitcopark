package jpabook.jpashop.service;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // JPA는 반드시 트랜잭션 안에서 실행된다.
@RequiredArgsConstructor // final 필드로 생성자를 만들어준다.
public class MemberService {

//    @Autowired 필드주입, 테스트에서 문제 생길 수 있음
    private final MemberRepository memberRepository;

/*
    //@Autowired // 생성 시점에 무엇에 의존하는지 알 수 있고, Mock도 넣을 수 있다. 생략가능
    //@RequiredArgsConstructor로 생략가능
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
*/

    /**
    * 회원 가입
    */
    @Transactional(readOnly = false) // default 값, true가 조회에 최적화 되어있음
    public Long join(Member member){
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // 멀티스레드에서 동시에 여러개의 이름이 생길 수 있는 상황이라, DB에 Unique제약 조건을 넣는 방법 등을 이용하는 것이 좋다
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findById(memberId).get();
    }


    @Transactional
    public Long update(Long memberId, String name) {
        Member findMember = findOne(memberId);
        findMember.setName(name);
        return findMember.getId();
    }
}
