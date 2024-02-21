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

            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

//            Member member2 = new Member();
//            member2.setUsername("member2");
//            em.persist(member2);

            em.flush();
            em.clear();

//            Member findMember = em.find(Member.class, member.getId());

//            Member findMember = em.getReference(Member.class, member1.getId()); //getReference()를 호출하는 시점엔 DB에 쿼리가 날라가지 않음 / findMember의 값을 실제 사용되는 시점엔 DB에 쿼리가 날라감
//            System.out.println("findMember = " + findMember.getClass()); // -> findMember = class hellojpa.Member$HibernateProxy$WO0oD6s5 - 프록시 클래스, 가짜 클래스
//            System.out.println("findMember.id = " + findMember.getId()); // 이미 getReference()로 찾을 때 id값을 찾아줬기에 이 부분은 쿼리가 안 날라감
//            System.out.println("findMember.username = " + findMember.getUsername());
//            System.out.println("findMember.username = " + findMember.getUsername()); // 위에서 한번 프록시 객체가 영속성 컨텍스트에 초기화 요청을 해서 DB가 실제 엔티티를 생성했기에 더이상 요청을 하지 않고 바로 DB에 접근해서 데이터를 가져온다.


//            Member refMember = em.getReference(Member.class, member1.getId()); // 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.getReference()를 호출해도 실제 엔티티 반환 - hellojpa.Member - 프록시로 반환해도 실제 얻는 이점이 없기 때문
//            System.out.println("refMember = " + refMember.getClass());
//
//            Member findMember = em.find(Member.class, member1.getId());
//            System.out.println("findMember = " + findMember.getClass());

            /*
            JPA - 같은 영속성 컨텍스트 안에서 조회를 하면 항상 같은 값이 나온다. - true
            그렇기 때문에 위에 refMember = proxy에서 받아오고 refMember == findMember = true가 성립되어야 하기에 findMember도 proxy에서 받아온다.
            핵심은 JPA가 proxy에서 조회하던 DB에서 조회하던 맞춰준다.
             */
//            System.out.println("refMember == findMember : " + (refMember == findMember)); //true

//            Member m2 = em.getReference(Member.class, member2.getId());
//
//            logic(m1, m2); // ctrl + alt + m - 메서드로 추출


            //준영속 상태일 때 프록시 초기화 문제 발생
//            Member refMember = em.getReference(Member.class, member1.getId());
//            System.out.println("refMember = " + refMember.getClass());
//
//            em.detach(refMember); //영속성 컨텍스트에서 빼버림 / 영속성 컨텍스트가 더이상 관리 X
////            em.close(); //영속성 컨텍스트 close -> 5.4.1.Final 버전부터 예외가 발생하지 않음
////            em.clear();
//
//            refMember.getUsername();


            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember = " + refMember.getClass());

//            refMember.getUsername(); //프록시 초기화
            Hibernate.initialize(refMember); //강제 초기화
            System.out.println("emf.getPersistenceUnitUtil().isLoaded(refMember) = " + emf.getPersistenceUnitUtil().isLoaded(refMember)); //프록시 인스턴스의 초기화 여부 확인


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
