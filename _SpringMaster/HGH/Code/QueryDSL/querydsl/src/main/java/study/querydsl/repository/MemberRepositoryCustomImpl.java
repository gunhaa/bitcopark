package study.querydsl.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.dto.QMemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.QTeam;

import java.util.List;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
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

    @Override
    public Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable) {
        QMember m = QMember.member;
        QTeam t = QTeam.team;

        QueryResults<MemberTeamDto> results = queryFactory
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
                // 몇번 부터 시작? (스킵할것)
                .offset(pageable.getOffset())
                // 페이지당 몇개임?
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MemberTeamDto> content = results.getResults();
        // 총 개수
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<MemberTeamDto> searchPageComplex(MemberSearchCondition condition, Pageable pageable) {
        QMember m = QMember.member;
        QTeam t = QTeam.team;

        List<MemberTeamDto> content = queryFactory
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
                // 몇번 부터 시작? (스킵할것)
                .offset(pageable.getOffset())
                // 페이지당 몇개임?
                .limit(pageable.getPageSize())
                .fetch();

        // 카운트쿼리가 join이 사용되지 않는 경우 사용되어 오버헤드가 나지 않게 함
        // 효율을 올리기 위한 방법이다.
        // 데이터가 몇 천만건이 있는 경우 심각하게 고민해야한다.
        long total = queryFactory
                .selectFrom(m)
                .leftJoin(m.team, t)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                )
                .fetchCount();

        JPAQuery<Member> countQuery = queryFactory
                .selectFrom(m)
                .leftJoin(m.team, t)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                );

                                                                // count 쿼리 최적화 - 특정 상황에서만 함수를 실행시켜준다.
        return PageableExecutionUtils.getPage(content, pageable, ()-> countQuery.fetchCount());
//        return new PageImpl<>(content, pageable, total);
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
