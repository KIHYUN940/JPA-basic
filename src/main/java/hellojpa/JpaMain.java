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

            List<Member> result = em.createQuery("select m from Member as m", Member.class) // JPQL - member 테이블이 아닌 객체를 대상으로 쿼리를 짠다.
                    .setFirstResult(0) // 페이지네이션 0~10 가져와
                    .setMaxResults(10)
                    .getResultList();

            for (Member member : result) {
                System.out.println("member.getName() = " + member.getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
