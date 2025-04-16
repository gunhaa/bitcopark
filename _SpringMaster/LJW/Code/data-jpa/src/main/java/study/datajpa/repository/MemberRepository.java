package study.datajpa.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    // @Query(name = "Member.findByUsername") 생략 가능
    // 기본 전략 : 선언한 "도메인 클래스.메소드 이름" 으로 Named 쿼리를 찾아서 실행
    //           없을 경우 메소드 이름으로 쿼리 생성 전략 사용
    // List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findByUsernameAndAge(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) " +
            "from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username = :name")
    List<Member> findMembers(@Param("name") String username);

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    Page<Member> findByAge(int age, Pageable pageable);

    @Query(value = "select m from Member m",
            countQuery = "select count(m.username) from Member m")
    Page<Member> findMemberAllCountBy(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    // @EntityGraph(attributePaths = {"team"})
    // @NamedEntityGraph 사용
    @EntityGraph("Member.all")
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    @EntityGraph(attributePaths = {"team"})
    List<Member> findByUsername(String username);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String name);

    @QueryHints(value = {@QueryHint(name = "org.hibernate.readOnly", value = "true")},
            forCounting = true)
    Page<Member> findByUsername(String name, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String name);
}
