package hellojpa;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());
//            findParent.getChildList().remove(0);
            em.remove(findParent);


            System.out.println("=======");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

//    private static void logic(Member m1, Member m2) {
//        System.out.println("m1 == m2 : " + (m1.getClass() == m2.getClass())); //false - m1, m2에 실제 객체가 들어올지 프록시로 들어올지 모르기 때문에 타입 비교는 절대 ==으로 하지 말고 instanceof 사용
//        System.out.println("m1 instanceof m2 : " + (m1 instanceof Member)); //true - 타입 비교는 꼭 instanceof 사용
//        System.out.println("m1 instanceof m2 : " + (m2 instanceof Member));
//    }

//    private static void printMember(Member member) {
//        System.out.println("member = " + member.getUsername());
//    }
//
//    private static void printMemberAndTeam(Member member) {
//        String username = member.getUsername();
//        System.out.println("username = " + username);
//
//        Team team = member.getTeam();
//        System.out.println("team = " + team.getName());
//    }
}
