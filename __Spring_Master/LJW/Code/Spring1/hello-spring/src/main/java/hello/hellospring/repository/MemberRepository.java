package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findbyId(Long id);
    Optional<Member> findbyName(String name);
    List<Member> findAll();
}
