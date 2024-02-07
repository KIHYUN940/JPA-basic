package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            Member member = new Member(); // 더미 member 생성
//            member.setId(1L);
//            member.setName("HelloA");
//
//            em.persist(member);

//            Member findMember = em.find(Member.class, 1L); // 조회
//            findMember.setName("HelloJPA"); // 수정

//            List<Member> result = em.createQuery("select m from Member as m", Member.class) // JPQL - member 테이블이 아닌 객체를 대상으로 쿼리를 짠다.
//                    .setFirstResult(0) // 페이지네이션 0~10 가져와
//                    .setMaxResults(10)
//                    .getResultList();
//
//            for (Member member : result) {
//                System.out.println("member.getName() = " + member.getName());
//            }

            //비영속 - 객체를 생성한 상태
//            Member member = new Member();
//            member.setId(101L);
//            member.setName("HelloJPA");
//
//            //영속 - 객체를 저장한 상태
//            System.out.println("===BEFORE===");
//            em.persist(member);

//            em.detach(member); // 회원 엔티티를 영속성 컨텍스트에서 분리 - 준영속 상태
//            em.remove(member); // 실제 DB 데이터 삭제 - 객체를 삭제한 상태

//            System.out.println("===AFTER===");

//            Member findMember1 = em.find(Member.class, 101L); // 영속성 컨텍스트 초기화 상태에서 1차 캐시에 가져올게 없을 때 DB에서 가져온다. -> 쿼리로 가져옴
//            Member findMember2 = em.find(Member.class, 101L); // 1차 캐시에서 조회 -> DB에서 조회 X
//
//            System.out.println("result = " + (findMember1 == findMember2)); //true - 영속 엔티티의 동일성 보장 - 같은 트랜잭션 안에서 실행 시

            //영속
//            Member member1 = new Member(150L, "A");
//            Member member2 = new Member(160L, "B");
//
//            em.persist(member1);
//            em.persist(member2); // --> 커밋 전까지 쓰기 지연 SQL 저장소에서 쿼리가 쌓임 / 버퍼링을 모아서 write 하는 이점을 얻을 수 있음

            //변경 감지
//            Member member = em.find(Member.class, 150L);
//            member.setName("ZZZZZ"); // 현재 DB에 150 : A값을 ZZZZZ로 변경 -> em.persist(member);를 쓸 필요가 없다. - 변경 감지

            //영속
            Member member = new Member(200L, "member200");
            em.persist(member);

            em.flush(); // 커밋 되기 전에 미리 쿼리를 보고싶을 때 강제로 flush() 호출 / DB에 미리 반영

            System.out.println("==============");

            tx.commit(); // 트랜잭션 커밋하는 시점에 영속성 컨텍스트에 있는 데이터가 DB로 -> 이 시점에 쿼리가 날라감
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
