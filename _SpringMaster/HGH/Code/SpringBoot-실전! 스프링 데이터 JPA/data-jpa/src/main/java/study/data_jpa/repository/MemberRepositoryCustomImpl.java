package study.data_jpa.repository;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import study.data_jpa.entity.Member;

import java.util.List;

@RequiredArgsConstructor
                        // Impl이라는 이름을 맞춰야 구현체를 만들어서 메소드를 호출한다.
                        // xml config로 바꿀 수 있지만 관행을 바꾸지 말자..
                        // QueryDSL을 사용할때 많이 사용한다.
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{

    private final EntityManager em;


    // JDBC 템플릿 등 native 쿼리를 쓰고 싶을 때
    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m")
                .getResultList();
    }
}
