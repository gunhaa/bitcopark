package study.data_jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.data_jpa.dto.MemberDto;
import study.data_jpa.entity.Member;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 구현하지 않아도 동작한다.
    // Query Method 기능
    List<Member> findByUsername(String username);

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    // 많이 사용하는기능, 메소드 이름으로 쿼리 생성은 너무 길다
    // 이름이 없는 named 쿼리이다, 애플리케이션 로딩 시점에 파싱을 해서 오류가 있다면 출력해서 오류를 잡기 좋다. 사용권장
    // 복잡해진다면 이 방법을 선택하는게 제일 좋다. (이름을 간단하고 행동을 명확하게 볼 수 있도록)
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.data_jpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    List<Member> findListByUsername(String username); // 컬렉션
    Member findMemberByUsername(String username); // 단건
    Optional<Member> findOptionalByUsername(String username); // 단건 optional
    // 페이징

    // bulkQuery
    @Modifying (clearAutomatically = true)
    // 필수이다. ExecuteUpdate()를 실행시키는 것과 같다고 생각하면 된다.
    @Query("update Member m set m.age = m.age +1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query(
//            value = "select m from Member m left join m.team t" ,
            countQuery = "select count(m) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);

    Slice<Member> findSliceByAge(int age, Pageable pageable);

    // fetch join은 필요한 정보를 연관된 팀을 한방 쿼리로 전부 가져온다.
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    // fetch join을 간편하기 위해 Spring Data JPA는 해당 옵션을 제공한다.
    // 간단한 쿼리에서는 @EntityGraph를 활용하고, 복잡해지면 jpql의 fetch join을 활용 하는 것이 좋다.
    @Override
    // fetch join과 같은 방식으로 동작한다.
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    // 해당 방식의 응용도 가능하다
    // 위 메서드와 같은 결과가 나온다.
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    // 위 메서드와 같은 결과가 나온다.
//    @EntityGraph(attributePaths = {"team"})
    @EntityGraph("Member.all")
    List<Member> findEntityGraphByUsername(@Param("username") String username);
}
