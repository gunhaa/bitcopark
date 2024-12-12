package jpabook.jpashop.repository;

import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {
    // select m from Member m where m.name = ?로 자동으로 짠다.
    List<Member> findByName(String name);
}
