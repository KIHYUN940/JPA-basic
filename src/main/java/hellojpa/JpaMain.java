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

            // 1:N 일대다 연관관계 예시

            Member member = new Member();
            member.setUsername("member1");

            em.persist(member);

            Team team = new Team();
            team.setName("teamA");

            //team 객체를 추가했는데 member 쪽에 update 쿼리문이 날라감 - 1:N -> 복잡해질 수 있기 때문에 다대일 연관관계로 매핑 지향 -> 단뱡향 설계 후 필요하면 양방향 설계 GO
            // 객체 지향적으론 일대다 설계가 맞을진 모르지만 다대일로 설계를 하면 DB 설계와 맞춰서 좀 더 유지보수하기 쉽게 만들 수 있다.
            team.getMembers().add(member);

            em.persist(team);

            System.out.println("=======");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
