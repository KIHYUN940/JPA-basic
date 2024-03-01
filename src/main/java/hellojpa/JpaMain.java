package hellojpa;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            //JPQL
//            List<Member> resultList = em.createQuery("select m from Member m where m.username like '%kim%'", Member.class)
//                    .getResultList();
//
//            for (Member member : resultList) {
//                System.out.println("member = " + member);
//            }

            //criteria 사용 준비 - JPQL에서 Criteria는 동적으로 쿼리를 생성하기 위한 방법 중 하나
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class);

            Root<Member> m = query.from(Member.class);
//            CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("username"), "kim"));
            CriteriaQuery<Member> cq = query.select(m);

            String username = "Hong";
            if (username != null) {
                cq = cq.where(cb.equal(m.get("username"), "kim"));
            }

            List<Member> resultList = em.createQuery(cq)
                    .getResultList();
            /*
            Criteria
            장점 : 1. 오타 시 컴파일 시점에 컴파일 오류가나서 잡기 쉽다.
                  2. 동적 쿼리를 짜기 좋다.

            단점 : SQL스럽지가 않다.

            JPA 스펙에는 있지만, 실무에서 잘 쓰지 않는다. -> 코드가 복잡해지기 때문에 유지보수가 힘들어짐
            -> Criteria 대신 QueryDSL 사용 권장 - 오픈소스 라이브러리

            QueryDSL
            - 문자가 아닌 자바코드로 JPQL을 작성할 수 있음
            - JPQL 빌더 역할
            - 컴파일 시점에 문법 오류를 찾을 수 있음
            - 동적 쿼리 작성 편리
            - 단순하고 쉽다
            - JPQL 문법을 알고 사용하면 된다.
            -> 실무 사용 권장
             */

            Member member = new Member();
            member.setUsername("member1");
            em.persist(member); //commit을 해야 DB에 데이터가 들어감

            //flush -> commit이나 query 날라갈 때 동작

            //네이티브 SQL
            List<Member> resultList1 = em.createNativeQuery("select MEMBER_ID, city, street, zipcode2, USERNAME from MEMBER", Member.class)
                    .getResultList();

            /*
            JDBC 직접 사용, SpringJdbcTemplate, MyBatis 등
            엔티티매니저 안에서 쿼리를 날리면 자동으로 flush 해줘서 DB에 데이터를 넣고 쿼리로 찾을 수 있는데,
            ex) dbconn.executeQuery("select * from member"); 같이 JDBC 커넥션을 직접 사용하거나, 스프링 JdbcTemplate, 마이바티스 등을 사용하는 경우
            강제로 수동 flush를 해줘야 DB에서 쿼리로 조회가 가능하다. -> em.flush를 해준 후 쿼리 코드 날려야 함
             */

            for (Member member1 : resultList1) {
                System.out.println("member1 = " + member1.getUsername());
            }

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
