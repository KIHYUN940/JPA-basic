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

            Address address = new Address("city", "street", "10000");

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(address);
            em.persist(member);

            // Address의 컬럼 값을 수정하고 싶을 때, 인스턴스를 통으로 복사해서 값을 넣어 set 해줘야 함
            Address newAddress = new Address("NewCity", address.getStreet(), address.getZipcode());
            member.setHomeAddress(newAddress);

            // 값 타입의 실제 인스턴스인 값을 공유하는 것은 위험 -> 대신 값(인스턴스)를 복사해서 사용
//            Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());
//
//            Member member2 = new Member();
//            member2.setUsername("member2");
//            member2.setHomeAddress(copyAddress);
//            em.persist(member2);

            // member1의 주소만 바꾸고 싶어 업데이트를 했지만 모든 멤버의 주소가 같이 업데이트 되는 문제 -> side Effect 발생
            // 임베디드 타입 같은 값 타입을 여러 엔티티에서 공유하면 위험하다.
//            member.getHomeAddress().setCity("newCity"); //생성자로만 값을 설정하고 수정자(Setter)를 만들지 않으면 객체 타입을 수정할 수 없게 원천 차단하여 set을 할 수 없게 만듦 / 내부적으로 사용을 해야겠으면 setter를 private으로 설정

            /*
            정리
            값 타입은 무조건 불변으로 만들어야(setter 허용 X) 나중에 부작용이 생기지 않는다.
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
