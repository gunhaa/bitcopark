package study.querydsl.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.dto.QMemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.QTeam;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

//    public MemberJpaRepository(EntityManager em) {
//        this.em = em;
//        this.queryFactory = new JPAQueryFactory(em);
//    }

    // 빈으로 등록되어 있다면 바로 injection 받을 수 있다.
    // querydslapplication 참고
//    public MemberJpaRepository(EntityManager em, JPAQueryFactory queryFactory) {
//        this.em = em;
//        this.queryFactory = queryFactory;
//    }

    // 빈으로 설정 시에는 @RequiredArgsConstructor 어노테이션으로도 대체 가능하다

    public void save(Member member){
        em.persist(member);
    }

    public Optional<Member> findById(Long id){
        Member findMember = em.find(Member.class, id);
        return Optional.ofNullable(findMember);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findAll_Querydsl(){
        QMember m = QMember.member;
        return queryFactory.selectFrom(m).fetch();
    }

    public List<Member> findByUsername(String username){
        return em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username" , username)
                .getResultList();
    }

    public List<Member> findByUsername_Querydsl(String username){
        QMember m = QMember.member;
        return queryFactory.selectFrom(m).where(m.username.eq(username)).fetch();
    }

    public List<MemberTeamDto> searchByBuilder(MemberSearchCondition condition){

        QMember m = QMember.member;
        QTeam t = QTeam.team;

        BooleanBuilder builder = new BooleanBuilder();
            // Spring에서 제공해주는 메소드로, null값 ""값을 모두 체크해준다.
        if(StringUtils.hasText(condition.getUsername())){
            builder.and(m.username.eq(condition.getUsername()));
        }

        if(StringUtils.hasText(condition.getTeamName())) {
            builder.and(t.name.eq(condition.getTeamName()));
        }

        if(condition.getAgeGoe() != null){
            builder.and(m.age.goe(condition.getAgeGoe()));
        }

        if(condition.getAgeLoe() != null){
            builder.and(m.age.loe(condition.getAgeLoe()));
        }

        return queryFactory
                .select(new QMemberTeamDto(
                        m.id.as("memberId"),
                        m.username,
                        m.age,
                        t.id.as("teamId"),
                        t.name.as("teamName")
                ))
                .from(m)
                .leftJoin(m.team, t)
                .where(builder)
                .fetch();
    }


    public List<MemberTeamDto> search(MemberSearchCondition condition){

        QMember m = QMember.member;
        QTeam t = QTeam.team;

        return queryFactory
                .select(new QMemberTeamDto(
                        m.id.as("memberId"),
                        m.username,
                        m.age,
                        t.id.as("teamId"),
                        t.name.as("teamName")
                ))
                .from(m)
                .leftJoin(m.team, t)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                )
                .fetch();
    }

    // where문의 장점은 코드를 재활용 할 수 있다는 것이다.
    public List<Member> searchMember(MemberSearchCondition condition){

        QMember m = QMember.member;
        QTeam t = QTeam.team;

        return queryFactory
                .selectFrom(m)
                .leftJoin(m.team, t)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
//                        ageGoe(condition.getAgeGoe()),
//                        ageLoe(condition.getAgeLoe())
                        // 해당 방식처럼 조립할 수 있다.
                        ageBetween(condition.getAgeLoe(), condition.getAgeGoe())
                )
                .fetch();
    }

    private BooleanExpression ageBetween(int ageLoe, int ageGoe){
        return ageGoe(ageGoe).and(ageLoe(ageLoe));
    }

    // 해당 predicate보단 booleanexpression이 사용하기 좋다.
    //private Predicate usernameEq(String username) {
    private BooleanExpression usernameEq(String username) {
        return StringUtils.hasText(username) ? QMember.member.username.eq(username) : null;
    }

    private BooleanExpression teamNameEq(String teamName) {
        return StringUtils.hasText(teamName) ? QTeam.team.name.eq(teamName) : null;
    }

    private BooleanExpression ageGoe(Integer ageGoe) {
        return ageGoe != null ? QMember.member.age.goe(ageGoe) : null;
    }

    private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe != null ? QMember.member.age.loe(ageLoe) : null;
    }

}
