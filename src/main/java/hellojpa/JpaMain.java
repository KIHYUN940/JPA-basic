package hellojpa;

import jakarta.persistence.*;
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

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity", "street", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            //값 타입 컬렉션
//            member.getAddressHistory().add(new Address("old1", "street", "10000"));
//            member.getAddressHistory().add(new Address("old2", "street", "10000"));

            //값 타입 컬렉션 -> 엔티티 사용
            member.getAddressHistory().add(new AddressEntity("old1", "street", "10000"));
            member.getAddressHistory().add(new AddressEntity("old2", "street", "10000"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("=====================");

            // memberId 조회 시 컬렉션 값 타입들은 지연로딩이라 불러오지 않는다.
            Member findMember = em.find(Member.class, member.getId());

//            List<Address> addressHistory = findMember.getAddressHistory();
//            System.out.println("---------------");
//            // ** 컬렉션 값 타입은 디폴트가 지연로딩 전략 사용 - 이 시점에 쿼리 나감
//            for (Address address : addressHistory) {
//                System.out.println("address.getCity() = " + address.getCity());
//            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            System.out.println("------------------");
            for (String favoriteFood : favoriteFoods) {
                System.out.println("favoriteFood = " + favoriteFood);
            }

            System.out.println("=============================");

            //homeCity -> newCity - 값 타입은 불변 객체로 설계해야하기 때문에 밑에처럼 수정을 하면 안된다.
//            findMember.getHomeAddress().setCity("newCity"); // X
            Address a = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity",a.getStreet(),a.getZipcode())); //값 타입 수정 시 새로운 인스턴스 생성해서 통으로 갈아 끼워야 함


            //값 타입 컬렉션 수정 - 치킨 -> 한식  - 이것도 String이기 때문에 수정하려면 통으로 갈아 끼워야 함
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            System.out.println("======================");

            //값 타입 컬렉션 - 주소 old1 -> new1  // Equals hashcode가 잘 구현되어 있어야 함
            /*
            값 타입 컬렉션을 수정할 때 자바의 equals와 hashcode 메서드가 제대로 구현되어 있어야 하는 이유는 컬렉션 내에서 해당 값의 동등성을 판단하기 위해서
            컬렉션은 객체의 동등성을 판단하기 위해 equals 메서드를 사용하는데 내부적만으로 객체의 동등성을 판단할 수 없기에 객체의 equals 메서드가 올바르게 구현되어 있어야 함
            String 클래스엔 내부적으로 이미 equals() 메서드가 구현되어 있기에 따로 구현을 하지 않아도 된다.
             */
//            findMember.getAddressHistory().remove(new Address("old1", "street", "10000"));
//            findMember.getAddressHistory().add(new Address("new1", "street", "10000"));
            // 값 타입 컬렉션에 변경 사항이 발생하면 주인 엔티티와 연관된 모든 데이터를 삭제하고 값 타입 컬렉션에 있는 현재 값 모두 다시 저장한다. -> 결론은 사용하면 안된다. -> 값 타입 컬렉션 대신 일대다 관계를 고려

            /*
            그럼 값 타입 컬렉션은 언제 쓰는가??
            값 타입 추적이 필요 없을 정도로 체크 박스 [치킨, 피자] 같은 되게 단순한 값을 넣을 때 사용한다. - & 값이 다 사라져도 상관 없거나 업데이트 칠 필요가 없을 때 사용
            이게 아닌 이상 값 타입 컬렉션을 사용하지 말고 엔티티를 만들어서 사용하자!

            * 식별자가 필요하고 지속해서 값을 추적, 변경해야 한다면 그것은 값 타입이 아닌 엔티티
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
