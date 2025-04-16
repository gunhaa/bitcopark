package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

//    @Autowired
//    public MemberService(MemberRepository memberRepository){
//        this.memberRepository=memberRepository;
//    }

    // 회원 가입
    @Transactional
    public Long join(Member member){
        ValidateDuplicateMember(member); // 중복회원 검증
        return memberRepository.save(member);
    }

    private void ValidateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Member member){
        return memberRepository.findOne(member.getId());
    }

}
