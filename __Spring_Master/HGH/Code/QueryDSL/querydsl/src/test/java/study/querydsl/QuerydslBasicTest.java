package study.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Expr;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import study.querydsl.dto.MemberDto;
import study.querydsl.dto.QMemberDto;
import study.querydsl.dto.UserDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.QTeam;
import study.querydsl.entity.Team;

import java.util.List;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    QMember m;
    QTeam t;

    // 테스트 실행 전 실행한다.
    @BeforeEach
    public void before(){
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
                .where(m.username.eq("member1"))
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


    @Test
    public void sort(){


        // 회원 정렬 순서
        // 1. 회원 나이 내림차순(desc)
        // 2. 회원 이름 올림차순(asc)
        // 단 2에서 회원 이름이 없으면 마지막에 출력(nulls last)
        em.persist(new Member(null, 100));
        em.persist(new Member("member5", 100));
        em.persist(new Member("member6", 100));

        List<Member> result = queryFactory
                .selectFrom(m)
                .where(m.age.eq(100))
                // nulllast nullfirst가 있음
                .orderBy(m.age.desc(), m.username.asc().nullsLast())
                .fetch();


        Member member5 = result.get(0);
        Member member6 = result.get(1);
        Member memberNull = result.get(2);
        Assertions.assertThat(member5.getUsername()).isEqualTo("member5");
        Assertions.assertThat(member6.getUsername()).isEqualTo("member6");
        Assertions.assertThat(memberNull.getUsername()).isNull();


    }

    @Test
    public void paging1(){
//        List<Member> result = queryFactory
//                .selectFrom(m)
//                .orderBy(m.username.desc())
//                // 앞에 몇개를 스킵할지, 0부터 시작
//                .offset
                    // 몇개를 가지고 올지
//                .limit(2)
//                .fetch();
//
//        Assertions.assertThat(result.size()).isEqualTo(2);

        // 페이징 객체를 얻어와서 처리하기
        QueryResults<Member> queryResults = queryFactory
                .selectFrom(m)
                .orderBy(m.username.desc())
                .offset(1)
                .limit(2)
                .fetchResults();

        Assertions.assertThat(queryResults.getTotal()).isEqualTo(4);
        Assertions.assertThat(queryResults.getLimit()).isEqualTo(2);
        Assertions.assertThat(queryResults.getOffset()).isEqualTo(1);
        Assertions.assertThat(queryResults.getResults().size()).isEqualTo(2);
    }

    @Test
    public void aggregation(){
        // 결과가 tuple로 나온다(querydsl이 제공하는 tuple)
        // 여러 타입이 있을때 꺼내올 수 있다.
        List<Tuple> result = queryFactory
                .select(
                        m.count(),
                        m.age.sum(),
                        m.age.avg(),
                        m.age.max(),
                        m.age.min()
                )
                .from(m)
                .fetch();

        Tuple tuple = result.get(0);
        Assertions.assertThat(tuple.get(m.count())).isEqualTo(4);
        Assertions.assertThat(tuple.get(m.age.sum())).isEqualTo(100);
        Assertions.assertThat(tuple.get(m.age.avg())).isEqualTo(25);
        Assertions.assertThat(tuple.get(m.age.max())).isEqualTo(40);
        Assertions.assertThat(tuple.get(m.age.min())).isEqualTo(10);

    }

    // live template 에서 tdd같은 방식으로 템플릿을 등록하면 된다.
    @Test
    public void group() {

        // 팀의 이름과 각 팀의 평균 연령을 구해라
        List<Tuple> result = queryFactory
                .select(
                        t.name,
                        m.age.avg()
                ).from(m)
                .join(m.team, t)
                .groupBy(t.name)
                .fetch();

        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);

        Assertions.assertThat(teamA.get(t.name)).isEqualTo("teamA");
        Assertions.assertThat(teamA.get(m.age.avg())).isEqualTo(15);

        Assertions.assertThat(teamB.get(t.name)).isEqualTo("teamB");
        Assertions.assertThat(teamB.get(m.age.avg())).isEqualTo(35);
    }

    @Test
    public void join(){

        // 팀 A에 소속된 모든 회원 찾기
        List<Member> result = queryFactory
                .selectFrom(m)
                // left,right,inner 등 다양한 조인이 가능하다.
                // 세타조인도 가능하다(연관관계가 없어도 조인 가능)
                .join(m.team, t)
                .where(t.name.eq("teamA"))
                .fetch();

//        Assertions.assertThat(result.get(0).getUsername()).isEqualTo("member1");
//        Assertions.assertThat(result.get(1).getUsername()).isEqualTo("member2");


        Assertions.assertThat(result)
                .extracting("username")
                .containsExactly("member1", "member2");

    }


    @Test
    public void theta_join(){
        // 회원의 이름이 팀 이름과 같은 회원 조회
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        List<Member> result = queryFactory
                .select(m)
                // 세타조인을 하는 방법
                // from전에 관계가 없는 2개를 나열한다.
                // 최적화는 db가 시키고, db마다 전략이 다르다.
                // 이 방식을 쓰면 외부 조인이 불가능하다.
                // 하지만 on을 사용하면 외부 조인이 가능하다.
                .from(m, t)
                .where(t.name.eq(m.username))
                .fetch();

        for (Member member : result) {
            System.out.println("member.getUsername() = " + member.getUsername());
        }

        Assertions.assertThat(result)
                .extracting("username")
                .containsExactly("teamA", "teamB");

    }

    @Test
    public void join_on_filtering(){
        // 1. 조인 대상 필터링
        // 회원과 팀을 조인하면서, 팀 이름이 teamA인 팀만 조인, 회원은 모두 조회
        // JPQL : select m, t from Member m left join m.team t on t.name = 'teamA'
        //on : join 전에 조건을 필터링
        //where : join 후에 조건을 필터링
        List<Tuple> results = queryFactory
                .select(m, t)
                .from(m)
                // member는 다 가져오지만, team 기준으로 다 가져온다.
//                .leftJoin(m.team, t).on(t.name.eq("teamA"))
                .join(m.team, t)
//                .on(t.name.eq("teamA"))
                // where절의 결과와 같다.
                .where(t.name.eq("teamA"))
                .fetch();

        for (Tuple tuple : results) {
            System.out.println("tuple = " + tuple);
        }

        // 결과
        /*
        left join이여서 null값도 가져온다.(team 이 다른 경우) on절때문에 team의 결과가 사라지게 된다.
        tuple = [Member(id=1, username=member1, age=10), Team(id=1, name=teamA)]
        tuple = [Member(id=2, username=member2, age=20), Team(id=1, name=teamA)]
        tuple = [Member(id=3, username=member3, age=30), null]
        tuple = [Member(id=4, username=member4, age=40), null]
        join을 한다면 다른 team은 조회되지 않게된다.
        tuple = [Member(id=1, username=member1, age=10), Team(id=1, name=teamA)]
        tuple = [Member(id=2, username=member2, age=20), Team(id=1, name=teamA)]
        즉, on절을 활용해 조인 대상을 필터링 할 때 외부조인이 아니라 내부조인을 사용하면
        where절에서 필터링 하는 것과 기능이 동일하다. 따라서 on절을 활용한 조인 대상 필터링을 사용할 때,
        내부조인이면 익숙한 where절로 해결할 수 있다.
        left join이 필요한 시점에는 어쩔 수 없이 on절을 사용해야한다.
        */

    }

    @Test
    public void join_on_no_relation(){
        // 회원의 이름이 팀 이름과 같은 회원 조회
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        List<Tuple> result = queryFactory
                .select(m, t)
                // 연관 관계 없는 엔티티 외부 조인
                // 회원의 이름이 팀 이름과 같은 대상 외부 조인
                .from(m)
                // 막조인을 한뒤, on절을 이용해 조건을 건다.
                .leftJoin(t).on(m.username.eq(t.name))
                .fetch();


        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
        /* 결과
        tuple = [Member(id=1, username=member1, age=10), null]
        tuple = [Member(id=2, username=member2, age=20), null]
        tuple = [Member(id=3, username=member3, age=30), null]
        tuple = [Member(id=4, username=member4, age=40), null]
        tuple = [Member(id=5, username=teamA, age=0), Team(id=1, name=teamA)]
        tuple = [Member(id=6, username=teamB, age=0), Team(id=2, name=teamB)]
        tuple = [Member(id=7, username=teamC, age=0), null]
        조건 때문에 team을 가져오지 못하지만, 조건에 맞는 경우 null이 아닌 team을 가져온다.
         */

    }

    @PersistenceUnit
    EntityManagerFactory emf;

    @Test
    public void fetchJoinNo(){
        em.flush();
        em.clear();
        // fetch join을 테스트할때는 영속성 컨텍스트를 지워야한다.

        Member findMember = queryFactory
                .selectFrom(m)
                .join(m)
                .where(m.username.eq("member1"))
                .fetchOne();
        System.out.println(findMember);

        // 로딩된 것인지 boolean으로 판별할 수 있는 방법
        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());

        Assertions.assertThat(loaded).as("페치 조인 미적용").isFalse();
    }

    @Test
    public void fetchJoinUse(){
        em.flush();
        em.clear();
        // fetch join을 테스트할때는 영속성 컨텍스트를 지워야한다.

        Member findMember = queryFactory
                .selectFrom(m)
                // fetchjoin문법
                .join(m.team, t).fetchJoin()
                .where(m.username.eq("member1"))
                .fetchOne();
        System.out.println(findMember);

        // 로딩된 것인지 boolean으로 판별할 수 있는 방법
        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());

        Assertions.assertThat(loaded).as("페치 조인 적용").isTrue();
    }

    @Test
    public void subQuery(){
        // 쿼리 안에 쿼리 사용하기
        // 나이가 가장 많은 회원을 조회

        QMember memberSub = new QMember("memberSub");

        List<Member> result = queryFactory
                .select(m)
                .from(m)
                .where(m.age.eq(
                        JPAExpressions
                                .select(memberSub.age.max())
                                .from(memberSub)
                ))
                .fetch();

        Assertions.assertThat(result).extracting("age")
                .containsExactly(40);

    }

    @Test
    public void subQueryGoe(){
        // 쿼리 안에 쿼리 사용하기
        // 나이가 평균 이상인 회원을 조회
        //goe() : 크거나 같다. (>=) // gt or equal
        //gt() : 크다
        //loe() : 작거나 같다 (<=)

        QMember memberSub = new QMember("memberSub");

        List<Member> result = queryFactory
                .select(m)
                .from(m)
                .where(m.age.goe(
                        JPAExpressions
                                .select(memberSub.age.avg())
                                .from(memberSub)
                ))
                .fetch();

        Assertions.assertThat(result).extracting("age")
                .containsExactly(30, 40);

    }

    @Test
    public void subQueryIn(){

        QMember memberSub = new QMember("memberSub");

        List<Member> result = queryFactory
                .select(m)
                .from(m)
                .where(m.age.in(
                        JPAExpressions
                                .select(memberSub.age)
                                .from(memberSub)
                                .where(memberSub.age.gt(10))
                ))
                .fetch();

        Assertions.assertThat(result).extracting("age")
                .containsExactly(20,30, 40);

    }

    @Test
    public void selectSubQuery(){

        QMember memberSub = new QMember("memberSub");

        List<Tuple> result = queryFactory
                .select(m.username,
                        JPAExpressions
                                .select(memberSub.age.avg())
                                .from(memberSub)
                ).from(m)
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }

        /*
        tuple = [member1, 25.0]
        tuple = [member2, 25.0]
        tuple = [member3, 25.0]
        tuple = [member4, 25.0]
        */


        // JPQL의 서브쿼리(중요)
        // select, where에선 가능하지만 from절(from절의 서브쿼리=인라인뷰)에선 불가능하다.
        // 해결 방법
        // 1. 서브쿼리를 join으로 변경한다(불가능 할 수도 있음)
        // 2. 어플리케이션에서 쿼리를 2번 분리해서 실행한다.
        // 3. nativeSQL을 사용한다.

        // 사실 from절의 서브쿼리는 나쁜경우가 많다.
        // 빠르게 렌더링 될 상황이아니라면 sql 한방에 나가게 하려고 sql을 몸 비트는 것보다
        // 여러번 쿼리를 날리는게 더 나은 패턴일 수 있다.(ex, admin page)
    }

    @Test
    public void basicCase(){
        // 가급적이면 사용하지 않는 것이 좋다..
        // 하지만 쓸 일이 있다면..
        List<String> result = queryFactory
                .select(m.age
                        .when(10).then("열살")
                        .when(20).then("스무살")
                        .otherwise("기타"))
                .from(m)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }

    }

    @Test
    public void complexCase(){
        List<String> result = queryFactory.select(new CaseBuilder()
                        .when(m.age.between(0, 20)).then("0~20살")
                        .when(m.age.between(21, 30)).then("21~30살")
                        .otherwise("기타")
                )
                .from(m)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }

    }

    @Test
    public void constant(){
        // 가끔 사용한다
        List<Tuple> result = queryFactory
                .select(m.username, Expressions.constant("A"))
                .from(m)
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @Test
    public void concat(){
        // username_age 만들기
        List<String> result = queryFactory
                // 타입이 문자가 아니라 안된다
                // .stringValue()가 쓸 일이 많다. Enum에도 사용 가능
                .select(m.username.concat("_").concat(m.age.stringValue()))
                .from(m)
                .where(m.username.eq("member1"))
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void simpleProjection(){

        /* querydsl에서 프로젝션이란
        - 프로젝션은 쿼리에서 반환할 데이터의 형태를 어떻게 구성할지를 정의하는 것이다.
        - 원하는 데이터를 찍어서 가져오는 것이 프로젝션이다
        */
        // 단일 프로젝션

        List<String> result = queryFactory
                .select(m.username)
                .from(m)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void tupleProjection(){

        // Tuple이 repository에서만 사용하는 것이 아닌 controller, service로 가는 것은 좋은 설계가 아니다.
        // 의존성이 전파되는 것은 나쁜 설계이다.
        List<Tuple> result = queryFactory
                .select(m.username, m.age)
                .from(m)
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple.get(m.username) = " + tuple.get(m.username));
            System.out.println("tuple.get(m.age) = " + tuple.get(m.age));
        }

    }

    @Test
    public void findDto(){
        // JPQL 불가능하다
        em.createQuery("select m.username, m.age from Member m" , MemberDto.class);
        // 이런식으로 작성해야한다. JPQL이 제공해주는 문법
        List<MemberDto> result = em.createQuery("select new study.querydsl.dto.MemberDto(m.username, m.age) from Member m", MemberDto.class).getResultList();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    public void findDtoBySetter(){
        List<MemberDto> result = queryFactory
                // getter setter를 사용해 DTO 객체로 매핑한다.
                .select(Projections.bean(MemberDto.class,
                        m.username,
                        m.age
                ))
                .from(m)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }

    }

    @Test
    public void findDtoByField(){
        List<MemberDto> result = queryFactory
                // 해당 방식은 getter setter가 필요없이 필드에 바로 값을 넣어준다.
                .select(Projections.fields(MemberDto.class,
                        m.username,
                        m.age
                ))
                .from(m)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }

    }

    @Test
    public void findDtoByConstructor(){
        List<MemberDto> result = queryFactory
                // 해당 방식은 생성자를 통해서 넣어준다. 대신 타입이 맞아야한다.
                .select(Projections.constructor(MemberDto.class,
                        m.username,
                        m.age
                ))
                .from(m)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }

    }

    @Test
    public void findUserDto1(){
        List<UserDto> result = queryFactory
                // 해당 방식은 필드를 통해 주입하므로, 이름이 맞아야한다.
                .select(Projections.fields(UserDto.class,
                        // 해당 방식으로 필드의 이름을 맞출수 있다.
                        m.username.as("name"),
                        m.age
                ))
                .from(m)
                .fetch();

        for (UserDto userDto : result) {
            System.out.println("userDto = " + userDto);
        }

    }


    @Test
    public void findUserDto2(){

        QMember memberSub = new QMember("memberSub");

        List<UserDto> result = queryFactory
                // userdto age에 서브쿼리를 이용해 max age를 넣기
                .select(Projections.fields(UserDto.class,
                        // 해당 방식으로 필드의 이름을 맞출수 있다.
                        m.username.as("name"),
                        ExpressionUtils.as(JPAExpressions
                                .select(memberSub.age.max())
                                .from(memberSub), "age")
                ))
                .from(m)
                .fetch();

        for (UserDto userDto : result) {
            System.out.println("userDto = " + userDto);
        }

    }

    @Test
    public void findDtoByConstructor2(){
        List<UserDto> result = queryFactory
                // 해당 방식은 생성자를 통해서 넣어준다. 대신 타입이 맞아야한다.
                // 다른 클래스여도 이름이 맞는다면 상관 없다.
                .select(Projections.constructor(UserDto.class,
                        m.username,
                        m.age
                ))
                .from(m)
                .fetch();

        for (UserDto userDto : result) {
            System.out.println("userDto = " + userDto);
        }

    }

    @Test
    public void findDtoByQueryProjection(){

        // 최선의 방법이다 - 오류가 있다면 컴파일 오류로 잡힌다.
        // 기존 방법은 런타임에 오류를 확인할 수 있어서 매우 위험하다.
        List<MemberDto> result = queryFactory
                // @QueryProjection 어노테이션을 통해 생성할 수 있다.
                .select(new QMemberDto(m.username, m.age))
                .from(m)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }


    }

    // 동적쿼리를 사용하는 두가지 방법
    @Test
    public void dynamicQuery_BooleanBuilder(){

        String usernameParam = "member1";
        Integer ageParam = null;

        List<Member> result = searchMember1(usernameParam, ageParam);
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember1(String usernameCond, Integer ageCond) {
                                                // 초기 조건을 넣을 수 있다.
        BooleanBuilder builder = new BooleanBuilder();

        if(usernameCond != null){
            builder.and(m.username.eq(usernameCond));
        }

        if(ageCond != null){
            builder.and(m.age.eq(ageCond));
        }

        return queryFactory
                .selectFrom(m)
                .where(builder)
                .fetch();
    }

    @Test
    public void dynamicQuery_WhereParam(){
        String usernameParam = "member1";
        Integer ageParam = 10;

        List<Member> result = searchMember2(usernameParam, ageParam);
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember2(String usernameCond, Integer ageCond) {
        return queryFactory
                .selectFrom(m)
//                .where(usernameEq(usernameCond), ageEq(ageCond))
                // 이런식으로 가능하다
                .where(allEq(usernameCond, ageCond))
                .fetch();
    }

    // booleanExpression으로 받을 수 있음 인터페이스라서
//    private Predicate usernameEq(String usernameCond) {
    private BooleanExpression usernameEq(String usernameCond) {
//        if(usernameCond == null){
//            return null;
//        }
//        return m.username.eq(usernameCond);
        // 동일 코드지만 가독성이 더 좋다
        return usernameCond != null ? m.username.eq(usernameCond) : null;
    }
    // booleanExpression으로 받을 수 있음 인터페이스라서
//    private Predicate ageEq(Integer ageCond) {
    private BooleanExpression ageEq(Integer ageCond) {
        if(ageCond == null){
            return null;
        }
        return m.age.eq(ageCond);
    }
    
//    private Predicate allEq(String usernameCond, Integer ageCond){
    // 이런식으로도 쓸 수 있음
    // 조립을 할 수 있는 것이 큰 장점이다.
    // 재활용도 할 수 있어서 큰 장점이다.
    private BooleanExpression allEq(String usernameCond, Integer ageCond){
        return usernameEq(usernameCond).and(ageEq(ageCond));
    }

    @Test
//    @Commit
    public void bulkUpdate(){



        // 영향을 받은 row수
        long count = queryFactory
                .update(m)
                .set(m.username, "비회원")
                .where(m.age.lt(28))
                .execute();
        
        // 쿼리 실행 전
        // 영속성 컨텍스트 ||  DB 상태
        // member1 = 10 -> DB member1
        // member2 = 20 -> DB member2
        // member3 = 30 -> DB member3
        // member4 = 40 -> DB member4

        // 쿼리가 실행 된 이후 ..
        // 영속성 컨텍스트 ||  DB 상태
        // member1 = 10 -> DB 비회원
        // member2 = 20 -> DB 비회원
        // member3 = 30 -> DB member3
        // member4 = 40 -> DB member4
        // 영속성 컨텍스트는 그대로 남아있지만, DB는 바뀌는게 update문이다.
        // 이런 상황에서 DB에서 해당 코드로 select를 해온다면?
//        List<Member> result = queryFactory
//                .selectFrom(m)
//                .fetch();
        // 해당 코드가 실행된다면, DB와 영속성 컨텍스트의 상황이 불일치하게 된다.
        // 그러면 JPA는 가져온 데이터를 영속성 컨텍스트에서 넣을 때 영속성 컨텍스트에 우선권을 부여해,
        // DB에서 가져온 정보를 버리게 된다.

        // 그럼 해결 방법은?
        // 이렇게하면 해결이 가능하다
        em.flush();
        em.clear();
        // 영속성 컨텍스트 초기화 시키고 select 실행
        List<Member> result = queryFactory
                .selectFrom(m)
                .fetch();
        for (Member member : result) {
            System.out.println("member = " + member);
        }
        
    }

    @Test
//    @Commit
    public void bulkAdd(){
        long count = queryFactory
                .update(m)
                // 더하기
//                .set(m.age, m.age.add(1))
                // 곱하기
                .set(m.age, m.age.multiply(2))
                .execute();
    }

    @Test
//    @Commit
    public void bulkDelete(){
        long count = queryFactory
                .delete(m)
                // 18살 이상 모두 삭제
                .where(m.age.gt(18))
                .execute();
    }

    @Test
    public void sqlFunction(){

        // sql function 호출하는 방법
        // replace를 호출한다.
        // idalect에 등록이 되있어야 사용이 가능하다
        List<String> result = queryFactory
                .select(Expressions.stringTemplate(
                        "function('replace', {0}, {1}, {2})",
                        m.username, "member", "M"))
                .from(m)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }

    }

    @Test
    public void sqlFunction2(){

        List<String> result = queryFactory
                .select(m.username)
                .from(m)
                .where(m.username.eq(
                        // Expressions.stringTemplate("function('lower' , {0})", m.username)))
                        // ANSI 표준 함수들은 해당 형태로 변환 가능하다(내장되어있다)
                        m.username.lower()))
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }

    }

}
