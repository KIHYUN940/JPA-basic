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

            Team team2 = new Team();
            team2.setName("TeamB");
            em.persist(team2);

            Member member = new Member();
            member.setUsername("member1");

            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());

            List<Member> members = findMember.getTeam().getMembers(); //양방향 연관관계 - member -> team -> member

            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
            }

//            member.setTeam(team2); // 연관관계 수정
//            em.persist(member);

            //조회
//            Member findMember = em.find(Member.class, member.getId());
//
//            Team findTeam = findMember.getTeam();
//
//            System.out.println("findTeam = " + findTeam.getName());
//
//            //다른 팀으로 바꾸고 싶을 때
//            Team newTeam = em.find(Team.class, 2L); // DB에 2번 팀이 있다고 가정
//            System.out.println("newTeam = " + newTeam.getName());
//            findMember.setTeam(newTeam);

//            System.out.println("findTeam = " + findTeam.getId());
            /*
            정리하자면 여기서 findTeam에 할당된 값은 TeamA라 findMember.setTeam(newTeam)을 호출한 후에도 findTeam 값은 TeamA인데,
            속성값은 변경되기 때문에 DB상에서는 member1과 TeamB가 매핑
            * findMember 객체의 속성은 변경되어 DB 상에서는 "member1"과 "TeamB"가 매핑되는 것
             */

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
