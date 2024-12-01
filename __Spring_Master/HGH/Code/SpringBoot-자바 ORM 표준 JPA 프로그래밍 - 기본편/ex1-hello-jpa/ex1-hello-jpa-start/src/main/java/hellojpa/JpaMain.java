package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        // 한 단위의 쿼리마다 em이 필수적이다.
        // db connection 받는것과 비슷하다.
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{
            // 비영속 상태
//            Member member = new Member();
//            member.setId(1L);
//            member.setName("HelloA");
            // 영속 상태로 변경
//            em.persist(member);

//            Member findMember = em.find(Member.class, 1L);
//            System.out.println("findMember.getId() = " + findMember.getId());
//            System.out.println("findMember.getName() = " + findMember.getName());

//            Member findMember = em.find(Member.class, 1L);
            // 바뀐 객체를 감지하면 transaction commit 시점에 update 쿼리를 날린다.
            // 굳이 저장할 필요가 없다.
            // JPA모든 변경사항은 트랜잭션 안에서만 실행되어야한다.
//            findMember.setName("HelloJPA");
            // JPQL은 대상이 테이블이 아니고 객체라고 생각하면 된다.
//            List<Member> result = em.createQuery("select m from Member as m", Member.class).setFirstResult(0).setMaxResults(10).getResultList();
//
//            for (Member member : result) {
//                System.out.println("member.getName() = " + member.getName());
//            }
//            Member member1 = new Member(150L, "A");
//            Member member2 = new Member(160L, "B");
//
//            em.persist(member1);
//            em.persist(member2);
//            System.out.println("==============");
            // 여기까지 Insert SQL을 데이터베이스에 보내지 않는다.

            // 커밋하는 순간 데이터베이스에 insert SQL을 보낸다.

//            Member member = new Member(321L, "name");
//            Member member = em.find(Member.class, 150L);
//            member.setName("ZZZZZZZZZZZZZ");
            // 여기까지만으로 데이터 변경 가능

/*            Member member = new Member();
            member.setUsername("user1");

            em.persist(member);*/

            System.out.println("===================");

            Team team = new Team();
            team.setName("TeamA");
//            team.getMembers().add(member);
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);
            em.persist(member);




            em.flush();
            em.clear();

            // 1차 캐시에서 가져와서 insert문은 늦게 나간다.
//            Member findMember = em.find(Member.class, member.getId());
//            List<Member> members = findMember.getTeam().getMembers();
//
//            for (Member m : members) {
//                System.out.println("m.getUsername() = " + m.getUsername());
//            }


            // commit하는 시점에 EntityManager(영속성 컨텍스트)에 flush()가 호출되고,
            // flush()는 스냅샷(처음 객체 생성 상태)과 현재 Entity를 비교해서 차이가 있으면 업데이트한다.
            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally{
            em.close();
        }
        emf.close();
    }
}
