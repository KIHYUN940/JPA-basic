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

//            Member member = em.find(Member.class, 1L);
//
//            printMember(member); // 아래처럼 Member와 Team을 가져오는 로직에선 연관관계 매핑으로 인해 둘다 가져와도 상관 없지만 지금처럼 member만 가져오려고 할 때 team이 매핑되어 있기에 team까지 가져오면 성능상 손해 -> JPA는 지연로딩, 프록시 등으로 해결
////            printMemberAndTeam(member);

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Team team2 = new Team();
            team2.setName("teamB");
            em.persist(team2);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(team2);
            em.persist(member2);

            //영속성 컨텍스트에 있는 데이터들을 DB에 옮기고 영속성 컨텍스트는 비워두기 위해서 사용
            em.flush();
            em.clear();

//            Member m = em.find(Member.class, member1.getId());

//            System.out.println("m.getTeam().getClass() = " + m.getTeam().getClass()); //Team - 지연 로딩 LAZY 설정해뒀기에 프록시로 조회 / EAGER로 설정하면 즉시 로딩 - 프록시가 아닌 진짜 객체로 조회
//
//            System.out.println("====================");
//            m.getTeam().getName(); //LAZY - 프록시 - 이 부분에서 쿼리가 날라감 - 실제 team을 사용하는 시점에(가져올 때가 아님) 초기화(DB조회) /// EAGER - 즉시 로딩 - 위에서 이미 조인했기에 쿼리가 날라가지 않음
//            System.out.println("====================");

//            List<Member> members = em.createQuery("select m from Member m", Member.class) // EAGER 즉시로딩 - JPQL 사용 시 N+1 문제 일으킬 수 있음 / em.find()는 JPA 내부적으로 다 찾아서 쿼리 날리기에 N+1 문제 발생 X
//                    .getResultList(); //LAZY 지연로딩으로 바꾸면 N+1 문제 해결 -> LAZY로 바꿨기 때문에 그 객체를 프록시 객체로 감싸져 따로 따로 로딩이 된다.


            //fetch join - LAZY를 사용하더라도 JPQL에서 fetch join을 하면 조인 가능 - 대부분 이 방법으로 해결 - 엔티티 그래프 기능 및 배치로 해결하는 방법도 있음
            List<Member> members2 = em.createQuery("select m from Member m join fetch m.team", Member.class)
                    .getResultList();

            /*
            N+1 문제
            - 첫 쿼리 1개를 날렸는데 그것 때문에 추가적인 N개의 쿼리가 더 나가서 문제 발생되는 것
             */


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
