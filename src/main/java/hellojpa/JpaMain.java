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
            //저장

            Team team = new Team();
            team.setName("TeamA");

            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
//            member.changeTeam(team); // 순수 객체 상태를 고려해서 항상 양쪽에 값을 설정 // member의 setTeam()을 하는 시점에 연관관계 편의 메서드를 생성해서 양방향 연관관계 설정 가능

            em.persist(member);

            team.addMember(member); //연관관계 편의 메서드는 member에서 team을 세팅하던 team에서 member를 세팅하던 하나를 골라서 진행 -> 둘다 해버리면 무한 루프에 걸릴 수도 있음

//            team.getMembers().add(member); // 순수 객체 상태를 고려해서 항상 양쪽에 값을 설정
                                            // flush, clear를 하면 영속성 컨텍스트에 데이터가 없고 DB에 있는걸 가져오기 때문에 JPA 내부적으로 동작해서 알아서 찾아준다. - 없으면 못 읽어온다.
                                            // 연관관계 편의 메서드를 생성해서 활용 가능

//            em.flush();
//            em.clear();

            Team findTeam = em.find(Team.class, team.getId());
            List<Member> members = findTeam.getMembers();

//            for (Member m : members) {
//                System.out.println("m = " + m.getUsername());
//            }

            System.out.println("findTeam = " + findTeam);

            System.out.println("==========");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
