package jpabook.jpashop.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

//    @PersistenceContext final이 있고, @RequiredArgsConstructor이 있다면 생략가능
    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        //       단건조회    타입   ,  PK
        return em.find(Member.class , id);
    }

    public List<Member> findAll(){
        // JPQL 은 객체를 대상으로 쿼리를 한다고 생각하면 된다.
        // alias m 을 Member m으로 준다.
        // from의 대상이 객체임
        return em.createQuery("select m from Member m" , Member.class).getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name" , Member.class)
                .setParameter("name" , name)
                .getResultList();
    }

}
