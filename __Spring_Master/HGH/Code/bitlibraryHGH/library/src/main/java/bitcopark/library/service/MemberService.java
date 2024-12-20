package bitcopark.library.service;

import bitcopark.library.entity.member.Address;
import bitcopark.library.entity.member.Member;
import bitcopark.library.entity.member.MemberGender;
import bitcopark.library.exception.EmailDuplicateException;
import bitcopark.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member joinMember(String email, String name, String phoneNumber, MemberGender gender, int birth, Address address){

        validateDuplicateMember(email);

        Member member = Member.createMember(email, name, phoneNumber, gender, birth, address);

        memberRepository.save(member);

        return member;
    }

    private void validateDuplicateMember(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new EmailDuplicateException("중복된 이메일입니다.");
        }
    }

}
