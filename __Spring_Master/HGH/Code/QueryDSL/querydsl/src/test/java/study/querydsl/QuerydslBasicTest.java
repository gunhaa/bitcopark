package study.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.Team;

import java.util.List;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    QMember m;

    // 테스트 실행 전 실행한다.
    @BeforeEach
    public void before(){
        queryFactory = new JPAQueryFactory(em);

        m = QMember.member;

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
    }

    @Test
    public void startJPQL(){
        //member1을 찾아라
        Member findMember = em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        Assertions.assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void startQuerydsl(){
//        QMember m = new QMember("m");

        QMember m = QMember.member;

        Member findMember = queryFactory.select(m)
                .from(m)
                .where(m.username.eq("member1"))
                .fetchOne();

        Assertions.assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void search(){

        Member findMember = queryFactory
                // select절과 from절을 합친것
                .selectFrom(QMember.member)
                .where(
                        QMember.member.username.eq("member1")
                        .and(QMember.member.age.eq(10))
                )
                        // and 대신 , 로 파라미터 여러개로 전달 가능하다
                        // or도 가능
                        // .or(QMember.member.age.eq(10)))
                        // 생각보다 더 다양한 쿼리로 사용 가능(JPQL에서 지원하는 것 모두)
                        // 강의 자료에 있음
                .fetchOne();
                // 결과 얻어오기 -> 강의 자료에 존재

        Assertions.assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void resultFetch(){
        // fetch는 리스트를 얻어온다.
        List<Member> fetch = queryFactory
                .selectFrom(m)
                .fetch();
        // fetchOne은 단건을 조회한다
        Member fetchOne = queryFactory.selectFrom(m)
                .fetchOne();

        //fetchFirst는 첫번째 항목을 가져온다
        Member fetchFirst = queryFactory.selectFrom(m)
                .fetchFirst();

        // fetchResults

        QueryResults<Member> results = queryFactory
                .selectFrom(m)
                .fetchResults();

        // optional처럼 다양한 메서드들을 쓸 수 있는 QueryResults로 감싸져서 나온다.
        // 페이징 처리용 .getTotals() 등
        List<Member> resultsResults = results.getResults();

        // count 쿼리가 나가게 된다.
        long total = queryFactory
                .selectFrom(m)
                .fetchCount();

    }


}
