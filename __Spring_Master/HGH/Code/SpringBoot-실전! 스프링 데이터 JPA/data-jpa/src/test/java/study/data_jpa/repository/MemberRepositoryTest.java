package study.data_jpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.data_jpa.dto.MemberDto;
import study.data_jpa.entity.Member;
import study.data_jpa.entity.MemberOld;
import study.data_jpa.entity.Team;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void testMember(){

//        MemberOld member = new MemberOld("memberA");
//        MemberOld savedMember = memberRepository.save(member);
//
//        Optional<MemberOld> byId = memberRepository.findById(savedMember.getId());
//        MemberOld findMember = byId.get();
//
//        org.assertj.core.api.Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
//        org.assertj.core.api.Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
//        org.assertj.core.api.Assertions.assertThat(findMember).isEqualTo(member);

    }

    @Test
    public void basicCRUD(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);


        // 단건 조회 검증
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        Assertions.assertThat(findMember1).isEqualTo(member1);
        Assertions.assertThat(findMember2).isEqualTo(member2);

        //리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(2);

        // 카운트 검증
        long count = memberRepository.count();
        Assertions.assertThat(count).isEqualTo(2);

        // 삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);
        long deletedCount = memberRepository.count();
        Assertions.assertThat(deletedCount).isEqualTo(0);

    }

    @Test
    public void findByUsernameAndAgeGreaterThan(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

        Assertions.assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        Assertions.assertThat(result.get(0).getAge()).isEqualTo(20);
        Assertions.assertThat(result.size()).isEqualTo(1);

    }

    @Test
    public void testQuery(){
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findUser("AAA", 10);
        Assertions.assertThat(result.get(0)).isEqualTo(member1);

    }

    @Test
    public void findUsernameListTest(){
        Member m1 = new Member("AAA" , 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<String> usernameList = memberRepository.findUsernameList();
        for (String s : usernameList) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void findMemberDto(){

        Team team = new Team("teamA");
        teamRepository.save(team);

        Member m1 = new Member("AAA" , 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        m1.setTeam(team);


        List<MemberDto> memberDto = memberRepository.findMemberDto();
        for (MemberDto dto : memberDto) {
            System.out.println("dto = " + dto);
        }
    }

    @Test
    public void findByNames(){
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));
        for (Member member : result) {
            System.out.println("member = " + member);
        }
    }

    @Test
    public void returnType(){
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        // List가 null이 아닌 것이 JPA에서 보장된다.
        List<Member> aaa = memberRepository.findListByUsername("AAA");
        // 단건조회는 없다면 null이 나온다.
        Member aaa1 = memberRepository.findMemberByUsername("AAA");
        Optional<Member> aaa2 = memberRepository.findOptionalByUsername("AAA");

    }

    @Test
    public void paging() {
        //given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;

        // sorting 조건 생략 가능
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        //when
        //**인터페이스 내에서 Count query를 작성해서 분리할 수 있다. 그대로 맡기면 count query도 join이 되어 나가서 성능에 문제가 생긴다**
        // 쿼리가 복잡하다면 count 쿼리를 분리해야한다.
        Page<Member> page = memberRepository.findByAge(age, pageRequest);
//        Slice<Member> page = memberRepository.findByAge(age, pageRequest);
        // List로 사용 가능하다.
        //List<Member> page = memberRepository.findByAge(age, pageRequest);

        // page 객체를 dto로 만드는 방법
        // 실무에서 자주 사용하는 방법이고 권장하는 방법이다.
//        Page<MemberDto> toMap = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));

        // then
        List<Member> content = page.getContent();
        long totalElements = page.getTotalElements();

        Assertions.assertThat(content.size()).isEqualTo(3);
        Assertions.assertThat(page.getTotalElements()).isEqualTo(5);
        Assertions.assertThat(page.getNumber()).isEqualTo(0);
        Assertions.assertThat(page.getTotalPages()).isEqualTo(2);
        Assertions.assertThat(page.isFirst()).isTrue();
        Assertions.assertThat(page.hasNext()).isTrue();

    }

    @Test
    public void slice() {
        //given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;

        // sorting 조건 생략 가능
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        //when
//        Page<Member> page = memberRepository.findByAge(age, pageRequest);
        Slice<Member> page = memberRepository.findByAge(age, pageRequest);

        // then
        List<Member> content = page.getContent();
//        long totalElements = page.getTotalElements();

        Assertions.assertThat(content.size()).isEqualTo(3);
//        Assertions.assertThat(page.getTotalElements()).isEqualTo(5);
        Assertions.assertThat(page.getNumber()).isEqualTo(0);
//        Assertions.assertThat(page.getTotalPages()).isEqualTo(2);
        Assertions.assertThat(page.isFirst()).isTrue();
        Assertions.assertThat(page.hasNext()).isTrue();

    }

    @Test
    public void bulkUpdate(){
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));

        // when
        int resultCount = memberRepository.bulkAgePlus(20);

//        em.flush();
//        em.clear();

        List<Member> result = memberRepository.findByUsername("member5");
        Member member5 = result.get(0);
        // 멤버 5번의 나이는?
        // 40살 - updateSQL은 마지막에 flush 된다.
        // **bulk연산에서 가장 주의해야할 점이다**
        // 바로 사용하고 싶다면 flush와 clear를 한 후 다음에 검색해야한다.
        // 혹은 Spring Data의 기능인 @Modifying의 옵션 (clearAutomatically = true)를 활성화 시키는 것도 같은 동작을 한다.
        System.out.println("member5 = " + member5);


        // then
        Assertions.assertThat(resultCount).isEqualTo(3);
    }

    @Test
    public void findMemberLazy(){
        //given
        // member1 -> teamA
        // member2 -> teamB

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        // 영속성컨텍스트의 캐시 정보를 DB에 반영시킨다.
        em.flush();
        // 캐시를 비운다.
        em.clear();
        
        // when
        // N(결과)+1문제 발생
        List<Member> members = memberRepository.findAll();

        /*
        select
        m1_0.member_id,
        m1_0.age,
        t1_0.team_id,
        t1_0.name,
        m1_0.username
    from
        member m1_0
    left join
        team t1_0
            on t1_0.team_id=m1_0.team_id
        */
//        List<Member> members = memberRepository.findMemberFetchJoin();

        for (Member member : members) {
            System.out.println("member.getUsername() = " + member.getUsername());
            // 해당 코드는 쿼리가 나가지 않는다. proxy객체를 확인할 수 있다.
            System.out.println("member.getTeam().getClass() = " + member.getTeam().getClass());
            // team이 lazyloading이라 없기 때문에 필요한 시점(지금) 검색 쿼리가 나가고, 해당 코드가 실행된다.
            System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
        }
    }


    @Test
    public void queryHint(){
        // given
        Member member1 = memberRepository.save(new Member("member1", 10));
        em.flush();
        em.clear();

        //when
        Member findMember = memberRepository.findReadOnlyByUsername("member1");
        // 체크를 하기 위해 메모리가 필요하다(비용이 든다)
        // 변경 안할거고 찾기만 할 경우 다른 옵션으로 비용을 최적화 할 수 있다.(hibernate가 기능 제공)
        findMember.setUsername("member2");
        // readonly를 사용한다면 더티체킹 쿼리가 나가지 않는다.
        // 메모리 최적화를 하기때문에 스냅샷이 없어서, 변경 감지가 되지 않는다.
        // 성능테스트해보고 이점이 있어야 최적화 하는게 좋다.
        // 실시간 조회 트래픽이 너무 많다면, redis를 사용해야한다.
        // 그 이전에 성능을 한번 최적화할수있는 방법 중 하나이다.

        /*
        | **특징**       | **@QueryHints**                            | **@Transactional(readOnly = true)**            |
        |----------------|--------------------------------------------|-----------------------------------------------|
        | **적용 대상**  | 특정 JPQL/Native Query에만 적용             | 트랜잭션 내 모든 작업에 적용                   |
        | **효과**       | Hibernate 1차 캐시에서 관리하지 않음         | JDBC와 Hibernate 모두 읽기 전용 모드로 작동    |
        | **사용 목적**  | 쿼리 성능 최적화(쓰기 감지 비활성화)         | 트랜잭션 범위에서 읽기 전용 작업 보장          |
        | **기술 스택 의존성** | Hibernate-specific                     | Spring Framework (JPA 또는 Hibernate와 결합)   |
        */

        em.flush();
    }

    @Test
    public void lock(){
        //given
        Member member1 = new Member("member1", 10);
        memberRepository.save(member1);
        em.flush();
        em.clear();

        //when
        // for update가 추가된 쿼리가 나간다.
        // 깊은 내용이라 이런것이 있다 정도로 이해하면 된다.
        // JPA가 제공하는 Lock을 어노테이션으로 쓸 수 있다.
        // JPA 책 참고(자세한 내용 적혀있음)
        // 실시간 서비스가 많은 서비스에서 사용하면 별로임
        Member findMember = memberRepository.findLockByUsername("member1");
    }

    @Test
    public void callCustom(){
        // QueryDSL을 사용할 때 많이 사용한다.
        // 복잡한 쿼리 사용할 때
        List<Member> result = memberRepository.findMemberCustom();
    }

    @Test
    public void JpaEventBaseEntity() throws Exception{

        //given
        Member member = new Member("member1");
        memberRepository.save(member);

        Thread.sleep(100);
        member.setUsername("member2");

        em.flush(); // @PreUpdate 메소드도 실행된다.
        em.clear();

        //when
        Member findMember = memberRepository.findById(member.getId()).get();

        //then
//        System.out.println("findMember = " + findMember.getCreatedDate());
////        System.out.println("findMember.getUpdateDate() = " + findMember.getUpdateDate());
//        System.out.println("findMember.getLastModifiedDate() = " + findMember.getLastModifiedDate());
//        System.out.println("findMember.getCreatedBy() = " + findMember.getCreatedBy());
//        System.out.println("findMember.getLastModifiedBy() = " + findMember.getLastModifiedBy());

    }

}