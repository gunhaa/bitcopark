package study.querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.QTeam;
import study.querydsl.entity.Team;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {


    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    QMember m;
    QTeam t;
    JPAQueryFactory queryFactory;

    @Test
    public void basicTest(){
        Member member = new Member("member1", 10);
        memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId()).get();
        Assertions.assertThat(findMember).isEqualTo(member);

        List<Member> result1 = memberRepository.findAll();
        Assertions.assertThat(result1).containsExactly(member);
//
//        List<MemberTeamDto> result2 = memberRepository.findByUsername("member1");
//        Assertions.assertThat(result2).containsExactly(member);

    }

    @Test
    public void searchTest(){

        queryFactory = new JPAQueryFactory(em);

        m = QMember.member;
        t = QTeam.team;
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);

        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        MemberSearchCondition condition = new MemberSearchCondition();

        condition.setAgeGoe(35);
        condition.setAgeLoe(40);
        condition.setTeamName("teamB");

        // 동적쿼리를 짤때는 기본적으로 limit이 있거나, 기본조건이 있어서 너무 많이 가져오는것을 제한해야한다.
//        List<MemberTeamDto> result = memberJpaRepository.searchByBuilder(condition);
        // 같은 동작을 하는 다른 방법(where 이용)
        List<MemberTeamDto> result = memberRepository.search(condition);

        Assertions.assertThat(result).extracting("username").containsExactly("member4");
    }

    @Test
    public void searchPageSimple(){

        queryFactory = new JPAQueryFactory(em);

        m = QMember.member;
        t = QTeam.team;
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);

        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        MemberSearchCondition condition = new MemberSearchCondition();
        PageRequest pageRequest = PageRequest.of(0, 3);

        Page<MemberTeamDto> result = memberRepository.searchPageSimple(condition, pageRequest);

        Assertions.assertThat(result.getSize()).isEqualTo(3);
        Assertions.assertThat(result.getContent()).extracting("username").containsExactly("member1", "member2", "member3");

    }

}