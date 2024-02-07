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
            Member member = new Member();
            member.setId(100L);
            member.setName("HelloJPA");

            //영속 - 객체를 저장한 상태
            System.out.println("===BEFORE===");
            em.persist(member);

//            em.detach(member); // 회원 엔티티를 영속성 컨텍스트에서 분리 - 준영속 상태
//            em.remove(member); // 실제 DB 데이터 삭제 - 객체를 삭제한 상태

            System.out.println("===AFTER===");

            tx.commit(); // 트랜잭션 커밋하는 시점에 영속성 컨텍스트에 있는 데이터가 DB로 -> 이 시점에 쿼리가 날라감
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
