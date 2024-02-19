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

            Movie movie = new Movie();
            movie.setDirector("aaaa");
            movie.setActor("bbbb");
            movie.setName("바람과 함께 사라지다.");
            movie.setPrice(10000);

            em.persist(movie);

            //영속성 컨텍스트에 있는 걸 DB에 다 날리고 영속성 컨텍스트를 비움
            em.flush();
            em.clear();

            Item findItem = em.find(Item.class, movie.getId());  // 구현 클래스마다 테이블 전략은 데이터를 넣을 땐 문제가 없지만 데이터를 찾을 때 성능이 저하되는 문제가 발생
            System.out.println("findItem = " + findItem);

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
