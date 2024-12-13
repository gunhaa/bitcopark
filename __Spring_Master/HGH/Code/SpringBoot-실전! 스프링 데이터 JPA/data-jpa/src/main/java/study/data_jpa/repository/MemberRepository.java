package study.data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.data_jpa.entity.Member;
import study.data_jpa.entity.MemberOld;

import java.util.List;

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
}
